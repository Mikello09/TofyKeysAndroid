package com.codebounds.tofyapp.registro

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.widget.Button
import com.codebounds.tofyapp.claves.ClavesActivity
import com.codebounds.tofyapp.helper.BaseActivity
import com.codebounds.tofyapp.R
import com.codebounds.tofyapp.configuracion.ConfiguracionActivity
import com.codebounds.tofyapp.databinding.ActivitySuccessSeguridadBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SuccessSeguridadActivity : BaseActivity() {

    private var fromRegister: Boolean = true
    private lateinit var binding: ActivitySuccessSeguridadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessSeguridadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()

        fromRegister = intent.getBooleanExtra("fromRegister", true)


        binding.lockAnimation.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {
                if (fromRegister){
                    var i = Intent(this@SuccessSeguridadActivity, ClavesActivity::class.java)
                    startActivity(i)
                } else {
                    var i = Intent(this@SuccessSeguridadActivity, ConfiguracionActivity::class.java)
                    startActivity(i)
                }
            }
            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationStart(p0: Animator?) {}
        })
    }
}