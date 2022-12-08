package com.example.apprepartidor.server

import com.example.apprepartidor.UserResponse
import com.example.apprepartidor.items.Mailbox
import com.example.apprepartidor.items.Paquete as Paquete
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface ClientService {
    //metodo de prueba para ver si funciona la conexion
    @POST("ServidorUbicua-0.0.1-SNAPSHOT/EstadoBuzon")
    suspend fun getSomething(): Response<String>

    //metodo para crear un nuevo usuario, usado en la pantalla de registro
    @POST("ServidorUbicua-0.0.1-SNAPSHOT/EstadoBuzon/")
    suspend fun createNewUser(@Body requestBody: UserResponse): Response<UserResponse>

    //metodo para validar un login
    @POST("ServidorUbicua-0.0.1-SNAPSHOT/ValidarUsuario")
    suspend fun validateUser(@Query("id") idCliente: Int, @Query("contraseña") password: String): Response<Paquete>

    //metodo para coger todos los paquetes de un repartidor
    @POST("ServidorUbicua-0.0.1-SNAPSHOT/Paquetes")
    suspend fun getPackages(@Query("id") idRepartidor: Int): Response<ArrayList<Paquete>>

    @POST("ServidorUbicua-0.0.1-SNAPSHOT/BuzonesLibres")
    suspend fun getFreeMailbox(): Response<ArrayList<Mailbox>>
}
















