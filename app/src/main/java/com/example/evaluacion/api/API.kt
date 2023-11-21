package com.example.evaluacion.api

import com.example.evaluacion.models.movimientos
import com.example.evaluacion.models.tarjeta
import retrofit2.Call
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface API {

    @GET
    fun selectMovimientos(
        @Url url: String
    ): Call<ArrayList<movimientos>>

    @GET
    fun selectTarjeta(
        @Url url: String
    ): Call<tarjeta>
}