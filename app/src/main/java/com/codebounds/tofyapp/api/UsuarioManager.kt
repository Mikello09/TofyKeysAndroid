package com.codebounds.tofyapp.api

import android.content.Context
import android.content.SharedPreferences
import com.codebounds.tofyapp.api.data.ClaveModel
import com.codebounds.tofyapp.api.data.TipoSeguridad
import com.codebounds.tofyapp.api.data.Usuario


class UsuarioManager{

    private val sharedPrefFile = "usuarioPreferencias"

    fun inicializarUsuario(context: Context){
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        Usuario.shared.seguridad = mapTipoSeguridad(sharedPreferences.getString("seguridad","ninguna") ?: "")
        Usuario.shared.pin = sharedPreferences.getString("pin","") ?: ""
        Usuario.shared.email = sharedPreferences.getString("email","") ?: ""
        Usuario.shared.contrasena = sharedPreferences.getString("contrasena","") ?: ""
        Usuario.shared.token = sharedPreferences.getString("token","") ?: ""
        Usuario.shared.primeraVez = sharedPreferences.getBoolean("primeraVez", true)
    }

    fun mapTipoSeguridad(tipo: String): TipoSeguridad {
        when (tipo){
            "ninguna" -> return TipoSeguridad.ninguna
            "pin" -> return TipoSeguridad.pin
            "biometria" -> return TipoSeguridad.biometria
            else -> return TipoSeguridad.ninguna
        }
    }

    fun mapTipoSeguridadToString(tipo: TipoSeguridad): String{
        when(tipo){
            TipoSeguridad.biometria -> return "biometria"
            TipoSeguridad.pin -> return "pin"
            TipoSeguridad.ninguna -> return "ninguna"
        }
    }

    fun guardarPin(pin: String, context: Context){
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("pin",pin).apply()
        Usuario.shared.pin = pin
    }

    fun guardarSeguridad(seguridad: TipoSeguridad, context: Context){
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("seguridad", mapTipoSeguridadToString(seguridad)).apply()
        Usuario.shared.seguridad = seguridad
    }

    fun guardarUsuario(email: String, pass: String, token: String, context: Context){
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("email", email).apply()
        sharedPreferences.edit().putString("pass", pass).apply()
        sharedPreferences.edit().putString("token", token).apply()
        Usuario.shared.email = email
        Usuario.shared.contrasena = pass
        Usuario.shared.token = token
    }

    suspend fun borrarUsuario(context: Context){
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
        Usuario.shared.email = ""
        Usuario.shared.contrasena = ""
        Usuario.shared.token = ""
        Usuario.shared.seguridad = TipoSeguridad.ninguna
        Usuario.shared.pin = ""
        Usuario.shared.primeraVez = true
        ClavesManager.shared.borrarClaves()
    }

    fun primeraVez(context: Context){
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("primeraVez", false).apply()
        Usuario.shared.primeraVez = false
    }

}