package com.codebounds.tofyapp.api.data


enum class TipoSeguridad{
    ninguna,
    pin,
    biometria
}

class Usuario{

    companion object {
        val shared = Usuario()
    }
    var seguridad: TipoSeguridad =
        TipoSeguridad.ninguna
    var pin: String = ""
    var email: String = ""
    var contrasena: String = ""
    var token: String = ""
    var primeraVez: Boolean = true
}

data class UsuarioModel(
    var usuario: UsuarioRespuesta
)

data class UsuarioRespuesta(
    var email: String,
    var pass: String,
    var token: String
)