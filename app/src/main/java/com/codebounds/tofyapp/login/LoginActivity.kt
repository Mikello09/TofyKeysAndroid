package com.codebounds.tofyapp.login

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.codebounds.tofyapp.api.UsuarioManager
import com.codebounds.tofyapp.claves.ClavesActivity
import com.codebounds.tofyapp.helper.*
import com.codebounds.tofyapp.helper.popups.PinPopUp
import com.codebounds.tofyapp.registro.RegistroActivity
import com.codebounds.tofyapp.api.data.TipoSeguridad
import com.codebounds.tofyapp.api.data.Usuario
import com.codebounds.tofyapp.databinding.ActivityLoginBinding
import com.codebounds.tofyapp.helper.popups.PinPopUpInterface
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), BiometriaResultadoInterface, PinPopUpInterface {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
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
        /////////////

        binding.registrateBoton.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.registrateBoton.setOnClickListener(View.OnClickListener {
            var i: Intent = Intent(this, RegistroActivity::class.java)
            startActivity(i)
        })

        binding.entrarBoton.setOnClickListener {
            hideKeyboard(binding.editTextTextEmailAddress)
            hideKeyboard(binding.editTextTextPassword)
            viewModel.onEntrarClicked(
                binding.editTextTextEmailAddress.text.toString(),
                binding.editTextTextPassword.text.toString())
        }

        observers()
    }

    fun observers(){
        viewModel.loginInit()
        viewModel.tipoSeguridad.observe(this, Observer { tipo ->
            when(tipo){
                TipoSeguridad.ninguna -> {
                    if(Usuario.shared.token != ""){
                        var i = Intent(this, ClavesActivity::class.java)
                        startActivity(i)
                    }
                }
                TipoSeguridad.pin -> { PinPopUp.newInstance(this).show(supportFragmentManager, "PinPopUp") }
                TipoSeguridad.biometria -> { Biometria(bioResultInterface = this).autenticarBiometria(this)}
            }
        })
        viewModel.usuario.observe(this, Observer { usuario ->
            hideLoader()
            UsuarioManager().guardarUsuario(
                usuario.usuario.email,
                usuario.usuario.pass,
                usuario.usuario.token,
                this
            )
            goToClaves()
        })
        viewModel.errorVisibility.observe(this, Observer { hideLoader() })

        binding.unlockAnimation.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                goToClaves()
            }
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}
        })
    }

    fun goToClaves(){
        var i = Intent(this, ClavesActivity::class.java)
        startActivity(i)
    }

    override fun biometriaSuccess() {
        goToClaves()
    }

    override fun biometriaFailed() {
        print("error en autenticación de biometria")
    }

    override fun mostrarPin() {
        PinPopUp.newInstance(this).show(supportFragmentManager, "PinPopUp")
    }

    override fun pinCorrecto() {
        viewModel.onPinCorrecto()
    }

    override fun onResume() {
        val imm = editTextTextEmailAddress.getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive)
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
        val imm2 = editTextTextPassword.getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm2.isActive)
            imm2.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
        super.onResume()
    }
}