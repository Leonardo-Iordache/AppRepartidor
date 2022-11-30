package com.example.apprepartidor

import com.google.gson.annotations.SerializedName

data class UserResponse(
    var ID: Int,
    @SerializedName("password") var contrasena: String,
    var name: String,
    var lastName: String,
    var dni: String,
)