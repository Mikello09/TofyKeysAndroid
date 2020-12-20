package com.codebounds.tofyapp.configuracion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.codebounds.tofyapp.R
import com.codebounds.tofyapp.api.UsuarioManager
import com.codebounds.tofyapp.databinding.ActivityConfiguracionBinding
import com.codebounds.tofyapp.helper.BaseActivity
import com.codebounds.tofyapp.helper.popups.CerrarSesionInterface
import com.codebounds.tofyapp.helper.popups.CerrarSesionPopup
import com.codebounds.tofyapp.login.LoginActivity
import kotlinx.coroutines.launch

class ConfiguracionActivity : BaseActivity(), CerrarSesionInterface {

    private lateinit var binding: ActivityConfiguracionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityConfiguracionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()

        binding.seguridadOption.setOnClickListener{
            var i: Intent = Intent(this, SeguridadConfiguracionActivity::class.java)
            startActivity(i)
        }

        binding.informacionOption.setOnClickListener {
            var i: Intent = Intent(this, InformacionActivity::class.java)
            startActivity(i)
        }

        binding.cerrarSesionOption.setOnClickListener {
            CerrarSesionPopup.newInstance(this).show(supportFragmentManager, "cerrarsesionpopup")
        }
    }

    override fun onCerrarSesion() {
        lifecycleScope.launch {
            UsuarioManager().borrarUsuario(this@ConfiguracionActivity)
        }
        var i: Intent = Intent(this, LoginActivity::class.java)
        startActivity(i)
    }
}