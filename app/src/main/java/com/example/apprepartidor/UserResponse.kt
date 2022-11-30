package com.example.apprepartidor

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("ID") var id: String,
    @SerializedName("contraseña") var password: String,
    @SerializedName("nombre") var name: String,
    @SerializedName("apellido") var lastName: String,
    @SerializedName("DNI") var dni: String,
)