package com.example.apprepartidor.server

import com.example.apprepartidor.UserResponse
import com.example.apprepartidor.items.Package as Paquete
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import retrofit2.http.Url

interface ClientService {
    @GET("ServidorUbicua-0.0.1-SNAPSHOT/")
    suspend fun getUserByID(): Response<UserResponse>

    @POST("/paquetes")
    suspend fun getPackageByID(@Url url: String): Response<Paquete>

    @POST("/paquetes")
    suspend fun getPackages(@Url url: String): Response<ArrayList<Paquete>>

    @PUT("/users")
    suspend fun putUserCredentials(@Url url: String, json: String)
}