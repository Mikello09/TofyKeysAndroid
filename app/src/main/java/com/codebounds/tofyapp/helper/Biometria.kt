package com.codebounds.tofyapp.helper

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity


interface BiometriaAccesibleInterface{
    fun biometriaAccesible()
    fun biometriaNoAccesible()
}

interface BiometriaResultadoInterface{
    fun mostrarPin()
    fun biometriaSuccess()
    fun biometriaFailed()
}

class Biometria{

    var bioAccessInterface: BiometriaAccesibleInterface?
    var bioResultInterface: BiometriaResultadoInterface?

    constructor(bioAccesInterface: BiometriaAccesibleInterface? = null, bioResultInterface: BiometriaResultadoInterface? = null){
        this.bioAccessInterface = bioAccesInterface
        this.bioResultInterface = bioResultInterface
    }

    fun isBiometriaAccesible(context: Context){
        val biometricManager = BiometricManager.from(context)

        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                this.bioAccessInterface?.biometriaAccesible()
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                this.bioAccessInterface?.biometriaNoAccesible()
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                this.bioAccessInterface?.biometriaNoAccesible()
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                this.bioAccessInterface?.biometriaNoAccesible()
        }
    }

    fun autenticarBiometria(context: FragmentActivity){
        val executor = ContextCompat.getMainExecutor(context)
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("TOFY")
            .setNegativeButtonText("Utilizar PIN")
            .setSubtitle("Seguridad biométrica")
            .setDescription("")
            .setDeviceCredentialAllowed(false)
            .build()

        val biometricPrompt = BiometricPrompt(context, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    bioResultInterface?.biometriaSuccess()
                }
                override fun onAuthenticationError(
                    errorCode: Int, errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    if(errString.equals("Utilizar PIN")){
                        bioResultInterface?.mostrarPin()
                    } else {
                        bioResultInterface?.biometriaFailed()
                    }
                }
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    bioResultInterface?.biometriaFailed()
                }
            })
        biometricPrompt.authenticate(promptInfo)
    }

}