package com.codebounds.tofyapp.api

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.codebounds.tofyapp.api.data.Clave
import com.codebounds.tofyapp.api.data.ClaveDb
import com.codebounds.tofyapp.api.data.Usuario
import kotlinx.coroutines.launch

class ClavesManager {

    companion object{
        val shared = ClavesManager()
    }

    private lateinit var room: ClaveDb
    private var clavesActualizadas: MutableLiveData<List<Clave>> = MutableLiveData<List<Clave>>()
    private var clavesLocal: List<Clave> = ArrayList<Clave>()
    private var clavesServidor: List<Clave> = ArrayList<Clave>()

    fun initDatabase(activity: AppCompatActivity){
        room = Room.databaseBuilder(activity,
            ClaveDb::class.java, "claveDB").build()
        room.claveDao().getAllClaves().observe(activity, Observer {
            clavesLocal = it
            clavesActualizadas.value = it
        })
    }

    fun getAllClaves(): LiveData<List<Clave>>{
        return clavesActualizadas
    }

    suspend fun sincronizarClave(clave: Clave){
        val result = kotlin.runCatching {
            val request = ServiceBuilder.buildService(ClaveEndPoints::class.java)
            request.guardarClave(
                Usuario.shared.token,
                clave.token,
                clave.titulo,
                clave.valor,
                clave.usuario,
                clave.contrasena,
                clave.fechaInclusion
            )
        }
        result.onSuccess{
            var claveSincronizadas = clavesLocal.filter { clave -> clave.token == it.clave.token }
            if (claveSincronizadas.size > 0){
                var claveSincronizada = claveSincronizadas.first()
                claveSincronizada.sincronizado = true
                editarClave(claveSincronizada)
            }
        }
    }

    suspend fun sincronizarClaves(){
        var clavesParaSincronizar = clavesLocal.filter { clave -> clave.sincronizado == false }
        for(clave in clavesParaSincronizar){
            sincronizarClave(clave)
        }
    }

    suspend fun guardarClave(claves: List<Clave>){
        room.claveDao().insert(claves)
        sincronizarClave(claves.get(0))
    }

    suspend fun editarClave(clave: Clave){
        room.claveDao().update(clave)
        sincronizarClave(clave)
    }

    suspend fun borrarClave(clave: Clave){
        val result = kotlin.runCatching {
            val request = ServiceBuilder.buildService(ClaveEndPoints::class.java)
            request.eliminarClave(clave.token,Usuario.shared.token)
        }
        result.onSuccess {
            room.claveDao().delete(clave)
        }
        result.onFailure {
            print("error")
        }
    }

    suspend fun borrarClaves(){
        room.claveDao().deleteAll()
    }

    suspend fun getClavesFromServer(){
        val result = kotlin.runCatching {
            val request = ServiceBuilder.buildService(ClaveEndPoints::class.java)
            request.getAllClaves(Usuario.shared.token)
        }
        result.onSuccess {
            var clavesAAñadir: ArrayList<Clave> = ArrayList<Clave>()
            for(clave in it.claves){
                val claveSincronizada = Clave(
                    clave.token,
                    clave.titulo,
                    clave.tokenUsuario,
                    clave.usuario,
                    clave.contrasena,
                    clave.valor,
                    true,
                    clave.fecha)
                clavesAAñadir.add(claveSincronizada)
            }
            room.claveDao().insert(clavesAAñadir)
        }
    }


}