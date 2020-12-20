package com.codebounds.tofyapp.login

import androidx.lifecycle.*
import com.codebounds.tofyapp.api.ServiceBuilder
import com.codebounds.tofyapp.api.UsuarioEndPoints
import com.codebounds.tofyapp.api.data.TipoSeguridad
import com.codebounds.tofyapp.api.data.Usuario
import com.codebounds.tofyapp.api.data.UsuarioModel
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel(){

    private val _errorVisibility = MutableLiveData<Boolean>()
    val errorVisibility: LiveData<Boolean> get() = _errorVisibility

    private val _showLoader = MutableLiveData<Boolean>()
    val showLoader: LiveData<Boolean> get() = _showLoader

    private val _tipoSeguridad = MutableLiveData<TipoSeguridad>()
    val tipoSeguridad: LiveData<TipoSeguridad> get() = _tipoSeguridad

    private val _usuario = MutableLiveData<UsuarioModel>()
    val usuario: LiveData<UsuarioModel> get() = _usuario

    private val _pinCorrecto = MutableLiveData<Boolean>()
    val pinCorrecto: LiveData<Boolean> get() = _pinCorrecto

    fun loginInit(){
        _tipoSeguridad.value = Usuario.shared.seguridad
    }

    fun onEntrarClicked(email: String, pass: String){
        if(email.isNullOrEmpty() || pass.isNullOrEmpty()){
            _errorVisibility.value = true
        } else {
            _showLoader.value = true
            viewModelScope.launch {
                val result = kotlin.runCatching {
                    val request = ServiceBuilder.buildService(UsuarioEndPoints::class.java)
                    request.doLogin(email.trim(),pass.trim())
                }
                result.onSuccess {
                    _errorVisibility.value = false
                    _showLoader.value = false
                    _usuario.value = it
                }.onFailure {
                    _errorVisibility.value = true
                    _showLoader.value = false
                }
            }
        }
    }

    fun onPinCorrecto(){
        _pinCorrecto.value = true
    }

}