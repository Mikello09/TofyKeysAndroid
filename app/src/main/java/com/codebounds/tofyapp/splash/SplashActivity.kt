package com.codebounds.tofyapp.splash

import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.codebounds.tofyapp.R
import com.codebounds.tofyapp.api.UsuarioManager
import com.codebounds.tofyapp.helper.BaseActivity
import com.codebounds.tofyapp.login.LoginActivity
import com.codebounds.tofyapp.databinding.ActivitySplashBinding
import java.util.*
import kotlin.concurrent.schedule


class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()

        Glide.with(this).load(R.drawable.tofy_keys_animacion).into(binding.splashImage)

        this.runOnUiThread(Runnable {
            Timer().schedule(2000){
                UsuarioManager().inicializarUsuario(this@SplashActivity)
                val i = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(i)
            }
        })

    }
}