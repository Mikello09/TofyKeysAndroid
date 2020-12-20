package com.codebounds.tofyapp.api

import com.codebounds.tofyapp.api.data.ClaveListaResponse
import com.codebounds.tofyapp.api.data.ClaveResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface ClaveEndPoints{

    @FormUrlEncoded
    @Headers("authtoken: AAAAA")
    @POST("/clave/getAllClaves")
    suspend fun getAllClaves(@Field("token") token: String): ClaveListaResponse


    @FormUrlEncoded
    @Headers("authtoken: AAAAA")
    @POST("/clave/guardarClave")
    suspend fun guardarClave(
        @Field("usuarioToken") usuarioToken: String,
        @Field("token") token: String,
        @Field("titulo") titulo: String,
        @Field("valor") valor: String,
        @Field("usuario") usuario: String,
        @Field("contrasena") contrasena: String,
        @Field("fecha") fecha: String
    ): ClaveResponse

    @FormUrlEncoded
    @Headers("authtoken: AAAAA")
    @POST("/clave/eliminarClave")
    suspend fun eliminarClave(
        @Field("token") token: String
    ): ClaveResponse



}