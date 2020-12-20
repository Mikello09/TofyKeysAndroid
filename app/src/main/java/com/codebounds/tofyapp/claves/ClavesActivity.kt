package com.codebounds.tofyapp.claves

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.codebounds.tofyapp.api.ClavesManager
import com.codebounds.tofyapp.api.UsuarioManager
import com.codebounds.tofyapp.configuracion.ConfiguracionActivity
import com.codebounds.tofyapp.api.data.*
import com.codebounds.tofyapp.api.data.Usuario.Companion.shared
import com.codebounds.tofyapp.helper.BaseActivity
import com.codebounds.tofyapp.databinding.ActivityClavesBinding
import com.codebounds.tofyapp.helper.popups.*
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class ClavesActivity : BaseActivity(), AddClavePopupInterface, ClaveDetallePopupInterface {

    private lateinit var binding: ActivityClavesBinding
    private lateinit var viewmodel: ClavesViewModel

    private lateinit var adapter: ClavesListaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityClavesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()
        //viewmodel
        viewmodel = ViewModelProvider(this).get()

        //init database
        ClavesManager.shared.initDatabase(this)
        viewmodel.onCreate()
        UsuarioManager().primeraVez(this)

        adapter =  ClavesListaAdapter() {claveSeleccionada ->
            ClaveDetallePopup.newInstance(claveSeleccionada,this).show(supportFragmentManager,"claveDetalle")
        }

        binding.clavesRecicler.layoutManager = GridLayoutManager(this, 1)
        binding.clavesRecicler.adapter = adapter


        binding.configuracionBoton.setOnClickListener {
            val i: Intent = Intent(this, ConfiguracionActivity::class.java)
            startActivity(i)
        }

        binding.anadirClaveBoton.setOnClickListener {
            AddClavePopup.newInstance(this).show(supportFragmentManager, "AddClavesPopup")
        }

        observers()
    }

    fun observers(){
        lifecycleScope.launch {
            ClavesManager.shared.getAllClaves().observe(this@ClavesActivity, Observer { lista ->
                adapter.update(lista)
            })
        }
    }

    override fun onClaveGuardado(
        token: String,
        tipo: TipoClave,
        titulo: String,
        valor: String,
        usuario: String,
        pass: String
    ) {
        viewmodel.guardarClave(Clave(
            token = token,
            titulo = titulo,
            valor = valor,
            usuario = usuario,
            contrasena = pass,
            usuarioToken = Usuario.shared.token,
            sincronizado = false,
            fechaInclusion = Date().toString()
        ))
    }

    override fun editarClave(clave: Clave) {
        viewmodel.updateClave(clave)
    }

    override fun eliminarClave(clave: Clave) {
        viewmodel.eliminarClave(clave)
    }
}