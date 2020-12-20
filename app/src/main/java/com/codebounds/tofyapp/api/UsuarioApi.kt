package com.codebounds.tofyapp.api

import com.codebounds.tofyapp.api.data.UsuarioModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface UsuarioEndPoints{

    @FormUrlEncoded
    @Headers("authtoken: AAAAA")
    @POST("/usuario/doLogin")
    suspend fun doLogin(@Field("email") email: String,
                        @Field("contrasena") pass: String)
            : UsuarioModel

    @FormUrlEncoded
    @Headers("authtoken: AAAAA")
    @POST("/usuario/registro")
    suspend fun registro(@Field("email") email: String,
                         @Field("pass") pass: String)
            : UsuarioModel

}