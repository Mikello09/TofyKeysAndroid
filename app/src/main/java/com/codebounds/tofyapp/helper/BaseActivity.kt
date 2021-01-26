package com.codebounds.tofyapp.helper

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.codebounds.tofyapp.claves.ClavesActivity
import com.codebounds.tofyapp.login.LoginActivity
import com.codebounds.tofyapp.R
import com.codebounds.tofyapp.configuracion.ConfiguracionActivity
import com.codebounds.tofyapp.configuracion.InformacionActivity
import com.codebounds.tofyapp.configuracion.SeguridadConfiguracionActivity
import com.codebounds.tofyapp.registro.PinActivity
import com.codebounds.tofyapp.registro.RegistroActivity
import com.codebounds.tofyapp.registro.SeguridadActivity
import com.codebounds.tofyapp.registro.SuccessSeguridadActivity
import com.codebounds.tofyapp.splash.SplashActivity


open class BaseActivity: AppCompatActivity(){

    lateinit var loader: View
    //lateinit var loaderImage: ImageView


    fun setActionBar() {
        when (this::class) {
            SplashActivity::class -> {
                supportActionBar?.hide()
            }
            LoginActivity::class -> {
                getSupportActionBar()?.hide()
            }
            RegistroActivity::class -> {
                val t = findViewById(R.id.toolbar) as Toolbar
                setSupportActionBar(t)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.title = ""
                addLoginToolBar(toolbar = t, title = "Registro")
            }
            SeguridadActivity::class -> {
                val t = findViewById(R.id.toolbar) as Toolbar
                setSupportActionBar(t)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.title = ""
                addLoginToolBar(toolbar = t, title = "Seguridad")
            }
            PinActivity::class -> {
                val t = findViewById(R.id.toolbar) as Toolbar
                setSupportActionBar(t)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.title = ""
                addLoginToolBar(toolbar = t, title = "PIN")
            }
            SuccessSeguridadActivity::class -> {
                val t = findViewById(R.id.toolbar) as Toolbar
                setSupportActionBar(t)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.title = ""
                addLoginToolBar(toolbar = t, title = "PIN")
            }
            ClavesActivity::class -> {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.title = ""
            }
            ConfiguracionActivity::class -> {
                val t = findViewById(R.id.toolbar) as Toolbar
                setSupportActionBar(t)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.title = ""
                addLoginToolBar(toolbar = t, title = "Menú")
            }
            InformacionActivity::class -> {
                val t = findViewById(R.id.toolbar) as Toolbar
                setSupportActionBar(t)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.title = ""
                addLoginToolBar(toolbar = t, title = "Información")
            }
            SeguridadConfiguracionActivity::class -> {
                val t = findViewById(R.id.toolbar) as Toolbar
                setSupportActionBar(t)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.title = ""
                addLoginToolBar(toolbar = t, title = "Seguridad")
            }
        }
    }

    fun addLoginToolBar(toolbar: Toolbar, title: String){
        var v = getLayoutInflater().inflate(R.layout.registro_toolbar, null)
        //var image = v.findViewById<ImageView>(R.id.toolBar_image)
        var titulo = v.findViewById<TextView>(R.id.toolBar_titulo)
        //image.clipToOutline = true
        titulo.text = title
        val l1 =
            Toolbar.LayoutParams(
                Toolbar.LayoutParams.MATCH_PARENT,
                80
            )
        v.setLayoutParams(l1)
        toolbar.addView(v)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                gestionarBotonAtras()
                return true
            }
            else -> {return super.onOptionsItemSelected(item)}
        }
    }

    override fun onBackPressed() {
        gestionarBotonAtras()
    }

    fun gestionarBotonAtras(){
        when(this::class){
            LoginActivity::class, ClavesActivity::class -> {
                print("do nothing")
            }
            ConfiguracionActivity::class -> {
                var i: Intent = Intent(this, ClavesActivity::class.java)
                startActivity(i)
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    fun createLoader(){
        /*loaderImage.clipToOutline = true
        val rotate = RotateAnimation(
            0.toFloat(),
            360.toFloat(),
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotate.duration = 1000
        rotate.interpolator = LinearInterpolator()
        rotate.repeatMode = Animation.INFINITE
        rotate.repeatCount = Animation.INFINITE
        loaderImage.startAnimation(rotate)*/
    }

    fun showLoader(){
        loader.visibility = View.VISIBLE
    }

    fun hideLoader(){
        loader.visibility = View.GONE
    }

    fun showKeyboard(){
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun hideKeyboard(editText: EditText){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0)
    }

}