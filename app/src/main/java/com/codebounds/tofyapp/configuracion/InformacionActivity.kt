package com.codebounds.tofyapp.configuracion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codebounds.tofyapp.BuildConfig
import com.codebounds.tofyapp.R
import com.codebounds.tofyapp.databinding.ActivityInformacionBinding
import com.codebounds.tofyapp.helper.BaseActivity

class InformacionActivity : BaseActivity() {

    private lateinit var binding: ActivityInformacionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformacionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()

        binding.version.text = "Versión: " + BuildConfig.VERSION_NAME
    }
}