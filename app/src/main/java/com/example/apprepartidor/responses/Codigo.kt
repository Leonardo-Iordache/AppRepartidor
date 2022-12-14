package com.example.apprepartidor.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Codigo(
    @SerializedName("idBuzon")
    @Expose
    val idBuzon: Int,

    @SerializedName("estado")
    @Expose
    val estado: Boolean,

    @SerializedName("codigo")
    @Expose
    val codigo: Int,

    @SerializedName("direccion")
    @Expose
    val direccion: String
)