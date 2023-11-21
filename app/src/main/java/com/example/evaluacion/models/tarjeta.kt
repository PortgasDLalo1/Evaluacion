package com.example.evaluacion.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class tarjeta(
    @SerializedName("pkTarjetaCreditoID") val TarjetaID: Int,
    @SerializedName("Nombre_Banco") val NBanco: String,
    @SerializedName("Numero_Tarjeta") val NTarjeta: String,
    @SerializedName("Titular_Tarjeta") val Titular: String,
    @SerializedName("Fecha_Exp") val Fecha: String,
    @SerializedName("CVV") val CVV: Int,
) {

    fun toJson(): String {
        return Gson().toJson(this)
    }

    override fun toString(): String {
        return "tarjeta(TarjetaID=$TarjetaID, NBanco='$NBanco', NTarjeta='$NTarjeta', Titular='$Titular', Fecha='$Fecha', CVV=$CVV)"
    }


}