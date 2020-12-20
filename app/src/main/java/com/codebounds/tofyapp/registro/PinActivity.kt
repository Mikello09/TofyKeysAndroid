package com.codebounds.tofyapp.registro

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.codebounds.tofyapp.api.UsuarioManager
import com.codebounds.tofyapp.helper.*
import com.codebounds.tofyapp.api.data.TipoSeguridad
import com.codebounds.tofyapp.databinding.ActivityPinBinding

class PinActivity : BaseActivity(), BiometriaAccesibleInterface {


    private lateinit var binding: ActivityPinBinding

    private var callFromRegister: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()

        callFromRegister = intent.getBooleanExtra("fromRegister", true)

        binding.botonPinSiguiente.setOnClickListener(View.OnClickListener {
            UsuarioManager().guardarPin(binding.pin1.text.toString() + binding.pin2.text.toString() + binding.pin3.text.toString() + binding.pin4.text.toString(), this)
            when(binding.switchSeguridad.isChecked){
                true -> UsuarioManager().guardarSeguridad(
                    TipoSeguridad.biometria, this)
                false -> UsuarioManager().guardarSeguridad(
                    TipoSeguridad.pin, this)
            }

            var i: Intent = Intent(this, SuccessSeguridadActivity::class.java)
            i.putExtra("fromRegister", callFromRegister)
            startActivity(i)
        })

        setPinEditText()
        if (callFromRegister){checkBiometria()}
        checkPin()

    }

    fun setPinEditText(){
        binding.pin1.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(!p0.isNullOrEmpty()){
                    if(p0.length == 2){
                        binding.pin1.setText(p0.get(1).toString())
                    }
                    binding.pin2.requestFocus()
                }
                checkPin()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })

        binding.pin2.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(!p0.isNullOrEmpty()){
                    if(p0.length == 2){
                        binding.pin2.setText(p0.get(1).toString())
                    }
                    binding.pin3.requestFocus()
                }
                checkPin()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })

        binding.pin3.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(!p0.isNullOrEmpty()){
                    if(p0.length == 2){
                        binding.pin3.setText(p0.get(1).toString())
                    }
                    binding.pin4.requestFocus()
                }
                checkPin()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })

        binding.pin4.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(!p0.isNullOrEmpty()){
                    if(p0.length == 2){
                        binding.pin4.setText(p0.get(1).toString())
                    }
                }
                checkPin()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

        })
    }

    fun checkPin(){
        if(binding.pin1.text.length != 0 && binding.pin2.text.length != 0 && binding.pin3.text.length != 0 && binding.pin4.text.length != 0){
            binding.botonPinSiguiente.visibility = View.VISIBLE
        } else {
            binding.botonPinSiguiente.visibility = View.INVISIBLE
        }
    }

    fun checkBiometria(){
        Biometria(this).isBiometriaAccesible(this)
    }

    override fun biometriaAccesible() {
        binding.pinBiometriaLayout.visibility = View.VISIBLE
    }

    override fun biometriaNoAccesible() {
        binding.pinBiometriaLayout.visibility = View.INVISIBLE
    }


}
