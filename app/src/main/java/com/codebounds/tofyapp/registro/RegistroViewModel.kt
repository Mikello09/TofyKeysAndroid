package com.codebounds.tofyapp.registro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codebounds.tofyapp.api.ServiceBuilder
import com.codebounds.tofyapp.api.UsuarioEndPoints
import com.codebounds.tofyapp.api.data.UsuarioModel
import com.codebounds.tofyapp.helper.isValidEmail
import kotlinx.coroutines.launch

class RegistroViewModel(): ViewModel(){


    private val _mensajeError = MutableLiveData<String>()
    val mensajeError: LiveData<String> get() = _mensajeError

    private val _errorVisible = MutableLiveData<Boolean>()
    val errorVisible: LiveData<Boolean> get() = _errorVisible

    private val _showLoader = MutableLiveData<Boolean>()
    val showLoader: LiveData<Boolean> get() = _showLoader

    private val _usuarioCreado = MutableLiveData<UsuarioModel>()
    val usuarioCreado: LiveData<UsuarioModel> get() = _usuarioCreado

    fun onRegistrarClicked(email: String, pass1: String, pass2: String){
        if (email.isNullOrEmpty()){
            _mensajeError.value = "El email no puede estar vacío"
            _errorVisible.value = true
            return
        }
        if (!email.isValidEmail()){
            _mensajeError.value = "El formato del email no es correcto"
            _errorVisible.value = true
            return
        }
        if (pass1.isNullOrEmpty() || pass2.isNullOrEmpty()){
            _mensajeError.value = "Tienes que introducir la contraseña dos veces"
            _errorVisible.value = true
            return
        }
        if(pass1.trim() != pass2.trim()){
            _mensajeError.value = "Las contraseñas deben ser iguales"
            _errorVisible.value = true
            return
        }
        _errorVisible.value = false
        _showLoader.value = true
        viewModelScope.launch {
            val result = kotlin.runCatching {
                val request = ServiceBuilder.buildService(UsuarioEndPoints::class.java)
                request.registro(email.trim(), pass1.trim())
            }
            result.onSuccess {
                _errorVisible.value = false
                _showLoader.value = false
                _usuarioCreado.value = it
            }
            .onFailure {
                _errorVisible.value = true
                _mensajeError.value = "Ha ocurrido un error. Vuelva a intentarlo"
                _showLoader.value = false
            }
        }


    }

}