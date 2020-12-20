package com.codebounds.tofyapp.registro

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.codebounds.tofyapp.api.UsuarioManager
import com.codebounds.tofyapp.helper.BaseActivity
import com.codebounds.tofyapp.databinding.ActivityRegistroBinding

class RegistroActivity : BaseActivity() {

    private lateinit var viewModel: RegistroViewModel
    private lateinit var binding: ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()

        //LOADER//
        loader = binding.loginLoader.loaderRootView
        //loaderImage = binding.loginLoader.loaderImage
        createLoader()
        /////////

        //VIEWMODEL//
        viewModel = ViewModelProvider(this).get()
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        //////////

        binding.crearCuentaBoton.setOnClickListener(View.OnClickListener {
            viewModel.onRegistrarClicked(
                binding.editTextTextEmailAddress4.text.toString(),
                binding.editTextTextPassword2.text.toString(),
                binding.editTextTextPassword3.text.toString())
        })

        observers()
    }

    fun observers(){
        viewModel.usuarioCreado.observe(this, Observer { usuario ->
            UsuarioManager().guardarUsuario(
                usuario.usuario.email,
                usuario.usuario.pass,
                usuario.usuario.token,
                this)
            var i: Intent = Intent(this, SeguridadActivity::class.java)
            startActivity(i)
        })
    }
}