package com.example.apprepartidor.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Paquete(
    @SerializedName("idPaquete")
    @Expose
    val idPaquete: Int,

    @SerializedName("direccion")
    @Expose
    val direccion: String,

    @SerializedName("estado")
    @Expose
    val estado: Int,

    @SerializedName("idBuzon")
    @Expose
    val idBuzon: Int,

    @SerializedName("idCliente")
    @Expose
    val idCliente: Int,

    @SerializedName("idRepartidor")
    @Expose
    val idRepartidor: Int
)