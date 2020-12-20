package com.codebounds.tofyapp.registro

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import com.codebounds.tofyapp.claves.ClavesActivity
import com.codebounds.tofyapp.helper.BaseActivity
import com.codebounds.tofyapp.databinding.ActivitySeguridadBinding

class SeguridadActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySeguridadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()



        binding.activarAhoraBoton.setOnClickListener(View.OnClickListener {
            val i = Intent(this,PinActivity::class.java)
            startActivity(i)
        })

        binding.masTardeBoton.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.masTardeBoton.setOnClickListener(View.OnClickListener {
            var i = Intent(this, ClavesActivity::class.java)
            startActivity(i)
        })
    }
}