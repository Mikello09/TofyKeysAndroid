package com.codebounds.tofyapp.configuracion

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.codebounds.tofyapp.R
import com.codebounds.tofyapp.api.UsuarioManager
import com.codebounds.tofyapp.api.data.TipoSeguridad
import com.codebounds.tofyapp.api.data.Usuario
import com.codebounds.tofyapp.databinding.ActivitySeguridadConfiguracionBinding
import com.codebounds.tofyapp.helper.BaseActivity
import com.codebounds.tofyapp.helper.Biometria
import com.codebounds.tofyapp.helper.BiometriaAccesibleInterface
import com.codebounds.tofyapp.registro.PinActivity

class SeguridadConfiguracionActivity : BaseActivity(), BiometriaAccesibleInterface {

    private lateinit var binding: ActivitySeguridadConfiguracionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySeguridadConfiguracionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()

        Biometria(bioAccesInterface = this).isBiometriaAccesible(this)

        when(Usuario.shared.seguridad){
            TipoSeguridad.ninguna -> {binding.radioGroup.check(R.id.ningunoRadioButton)}
            TipoSeguridad.pin -> {binding.radioGroup.check(R.id.pinRadioButton)}
            TipoSeguridad.biometria -> {binding.radioGroup.check(R.id.biometriaRadioButton)}
        }

        binding.radioGroup.setOnCheckedChangeListener { group, i ->
            when(i){
                R.id.ningunoRadioButton -> {
                    if(Usuario.shared.seguridad != TipoSeguridad.ninguna){
                        binding.botonGuardarSeguridad.text = "Guardar"
                        binding.botonGuardarSeguridad.visibility = View.VISIBLE
                    } else {
                        binding.botonGuardarSeguridad.visibility = View.GONE
                    }
                }
                R.id.pinRadioButton -> {
                    if(Usuario.shared.seguridad != TipoSeguridad.pin){
                        binding.botonGuardarSeguridad.text = "Configurar PIN"
                        binding.botonGuardarSeguridad.visibility = View.VISIBLE
                    } else {
                        binding.botonGuardarSeguridad.visibility = View.GONE
                    }
                }
                else -> {
                    if(Usuario.shared.seguridad != TipoSeguridad.biometria){
                        binding.botonGuardarSeguridad.text = "Activar biometría"
                        binding.botonGuardarSeguridad.visibility = View.VISIBLE
                    } else {
                        binding.botonGuardarSeguridad.visibility = View.GONE
                    }
                }
            }
        }

        binding.botonGuardarSeguridad.setOnClickListener {
            when(binding.radioGroup.checkedRadioButtonId){
                R.id.ningunoRadioButton -> {
                    UsuarioManager().guardarSeguridad(TipoSeguridad.ninguna, this)
                    finish()
                }
                R.id.pinRadioButton -> {
                    val i: Intent = Intent(this, PinActivity::class.java)
                    i.putExtra("fromRegister", false)
                    startActivity(i)
                }
                else -> {
                    UsuarioManager().guardarSeguridad(TipoSeguridad.biometria, this)
                    finish()
                }
            }
        }
    }

    override fun biometriaAccesible() {
        binding.biometriaRadioButton.visibility = View.VISIBLE
    }

    override fun biometriaNoAccesible() {
        binding.biometriaRadioButton.visibility = View.GONE
    }
}