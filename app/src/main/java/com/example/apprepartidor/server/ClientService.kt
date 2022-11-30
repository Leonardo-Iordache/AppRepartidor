package com.example.apprepartidor.server

import com.example.apprepartidor.UserResponse
import com.example.apprepartidor.items.Package as Paquete
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ClientService {
    @GET("/users")
    suspend fun getUserByID(@Url url: String): Response<UserResponse>

    @GET("/paquetes")
    suspend fun getPackageByID(@Url url: String): Response<Paquete>

    @POST("/users")
    suspend fun postUserCredentials(@Url url: String, json: String)
}