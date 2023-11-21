package com.example.evaluacion.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class movimientos(

    @SerializedName("pkMovimientosID") val pkMovimientosID: Int,
    @SerializedName("Descripcion") val Descripcion: String,
    @SerializedName("Fecha") val Fecha: String,
    @SerializedName("Monto") val Monto: Int

) {

    fun toJson(): String {
        return Gson().toJson(this)
    }

    override fun toString(): String {
        return "movimientos(pkMovimientosID=$pkMovimientosID, Descripcion='$Descripcion', Fecha='$Fecha', Monto=$Monto)"
    }
}