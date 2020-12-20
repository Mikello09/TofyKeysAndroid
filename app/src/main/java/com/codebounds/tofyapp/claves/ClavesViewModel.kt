package com.codebounds.tofyapp.claves

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codebounds.tofyapp.api.ClavesManager
import com.codebounds.tofyapp.api.UsuarioManager
import com.codebounds.tofyapp.api.data.*
import kotlinx.coroutines.launch

class ClavesViewModel: ViewModel(){


    fun onCreate(){
        if(Usuario.shared.primeraVez){
            viewModelScope.launch {
                ClavesManager.shared.getClavesFromServer()
            }
        }
        viewModelScope.launch {
            ClavesManager.shared.sincronizarClaves()
        }
    }

    fun guardarClave(clave: Clave){
        var clavesAAñadir: MutableList<Clave> = ArrayList<Clave>()
        clavesAAñadir.add(clave)
        viewModelScope.launch {
            ClavesManager.shared.guardarClave(clavesAAñadir)
        }
    }

    fun eliminarClave(clave: Clave){
        viewModelScope.launch {
            ClavesManager.shared.borrarClave(clave)
        }
    }

    fun updateClave(clave: Clave){
        viewModelScope.launch {
            ClavesManager.shared.editarClave(clave)
        }
    }

}