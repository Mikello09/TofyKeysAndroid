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
import com.codebounds.tofyapp.helper.popups.SeguridadCambiadaPopup
import com.codebounds.tofyapp.helper.popups.SeguridadCambiadaPopupInterface
import com.codebounds.tofyapp.registro.PinActivity


class SeguridadConfiguracionActivity : BaseActivity(), BiometriaAccesibleInterface, SeguridadCambiadaPopupInterface {

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

        binding.ningunoRadioButton.setOnCheckedChangeListener{buttonView, isChecked ->
            if (isChecked){
                binding.pinRadioButton.isChecked = false
                binding.biometriaRadioButton.isChecked = false
                if(Usuario.shared.seguridad != TipoSeguridad.ninguna){
                    binding.botonGuardarSeguridad.text = "Guardar"
                    binding.botonGuardarSeguridad.visibility = View.VISIBLE
                } else {
                    binding.botonGuardarSeguridad.visibility = View.GONE
                }
            }
        }

        binding.pinRadioButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                binding.ningunoRadioButton.isChecked = false
                binding.biometriaRadioButton.isChecked = false
                if(Usuario.shared.seguridad != TipoSeguridad.pin){
                    binding.botonGuardarSeguridad.text = "Configurar PIN"
                    binding.botonGuardarSeguridad.visibility = View.VISIBLE
                } else {
                    binding.botonGuardarSeguridad.visibility = View.GONE
                }
            }
        }

        binding.biometriaRadioButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                binding.ningunoRadioButton.isChecked = false
                binding.pinRadioButton.isChecked = false
                if(Usuario.shared.seguridad != TipoSeguridad.biometria){
                    binding.botonGuardarSeguridad.text = "Activar biometría"
                    binding.botonGuardarSeguridad.visibility = View.VISIBLE
                } else {
                    binding.botonGuardarSeguridad.visibility = View.GONE
                }
            }
        }

        binding.botonGuardarSeguridad.setOnClickListener {
            if(binding.ningunoRadioButton.isChecked){
                UsuarioManager().guardarSeguridad(TipoSeguridad.ninguna, this)
                SeguridadCambiadaPopup.newInstance(this,TipoSeguridad.ninguna).show(supportFragmentManager, "TipoSeguridadCambiado")
            } else if(binding.pinRadioButton.isChecked){
                val i: Intent = Intent(this, PinActivity::class.java)
                i.putExtra("fromRegister", false)
                startActivity(i)
            } else {
                UsuarioManager().guardarSeguridad(TipoSeguridad.biometria, this)
                SeguridadCambiadaPopup.newInstance(this,TipoSeguridad.biometria).show(supportFragmentManager, "TipoSeguridadCambiado")
            }
        }
    }

    override fun biometriaAccesible() {
        binding.biometriaRadioButton.visibility = View.VISIBLE
    }

    override fun biometriaNoAccesible() {
        binding.biometriaRadioButton.visibility = View.GONE
    }

    override fun ok() {
        finish()
    }
}

