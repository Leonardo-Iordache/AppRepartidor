package com.example.apprepartidor.server

import com.example.apprepartidor.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ClientService {
    @GET("/users/{id}")
    suspend fun getUserByID(@Url url: String): Response<UserResponse>
}