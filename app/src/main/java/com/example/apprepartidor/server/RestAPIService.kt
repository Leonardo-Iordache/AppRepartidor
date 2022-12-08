package com.example.apprepartidor.server

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.apprepartidor.items.Paquete
import com.example.apprepartidor.UserResponse
import com.example.apprepartidor.items.Mailbox
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class RestAPIService {
    private val serverURL = "http://25.60.44.198:8080/"

    /*fun searchUserByID(retrofit: Retrofit, serverURL: String, userResponse: UserResponse): UserResponse {
        CoroutineScope(Dispatchers.IO).launch {
            val id: Int
            val call =
                retrofit.create(ClientService::class.java).getUserByID()
            val user = call.body()

            if (call.isSuccessful) {
                id = user?.id?.toInt() ?:
                val contrasena = (user?.password ?: String) as String
                val name = (user?.name ?: String) as String
                val lastName = (user?.lastName ?: String) as String
                val dni = (user?.dni ?: String) as String

            } else {
                Log.d(this.javaClass.name, "Error al conectar con el servidor:${serverURL}")
            }
            return UserResponse(id, contrasena)
        }
    }*/


    //añade un nuevo usuario a los
    fun addUser(userData: UserResponse, context: Context){
        CoroutineScope(Dispatchers.IO).launch {
            val retrofit = ServiceBuilder.buildService(ClientService::class.java)
            val call = retrofit.createNewUser(userData)
            if (call.isSuccessful) {
                Toast.makeText(
                    context, "Usuario registrado con exito!!", Toast.LENGTH_SHORT
                ).show()
                Log.d(this.javaClass.name, "Correcto")
            } else {
                Toast.makeText(
                    context, "Error al registrar el usuario", Toast.LENGTH_SHORT
                ).show()
                Log.d(this.javaClass.name, "Error en POST:${serverURL}")
            }
        }
    }

    fun validateUser(userID: Int, userPassword: String): Boolean{
        var result: Boolean = false
        CoroutineScope(Dispatchers.IO).launch {
            val retrofit = ServiceBuilder.buildService(ClientService::class.java)
            val call = retrofit.validateUser(userID, userPassword)
            if(call.isSuccessful){
                val response = call.body()
                result = true
            }
        }
        return result
    }

    //funcion de prueba para un GET
    fun getSomething(){
        CoroutineScope(Dispatchers.IO).launch {
            val retrofit = ServiceBuilder.buildService(ClientService::class.java)
            val call = retrofit.getSomething()
            if(call.isSuccessful){
                val response = call.body()
                Log.d(this.javaClass.name, call.toString())
                Log.d(this.javaClass.name, response.toString())
            }
            else{
                Log.d(this.javaClass.name, "Error en POST: $serverURL")
            }
        }
    }

    fun getAllPackages(id: Int): ArrayList<Paquete>{
        val paqueteTemporal: ArrayList<Paquete> = ArrayList()
        CoroutineScope(Dispatchers.IO).launch {
            val retrofit = ServiceBuilder.buildService(ClientService::class.java)
            val call = retrofit.getPackages(id)
            if(call.isSuccessful){
                val response = call.body()
                response?.let {
                    for(i in it){
                        paqueteTemporal.add(i)
                    }
                }
            }
            else{
                Log.d(this.javaClass.name, "Error en getAllPackages: $serverURL")
            }
        }
        return paqueteTemporal
    }

    fun getFreeMailboxes(): ArrayList<Mailbox>{
        val mailboxes: ArrayList<Mailbox> = ArrayList()
        CoroutineScope(Dispatchers.IO).launch {
            val retrofit = ServiceBuilder.buildService(ClientService::class.java)
            val call = retrofit.getFreeMailbox()
            if(call.isSuccessful){
                val response = call.body()
                response?.let{
                    for(i in it){
                        mailboxes.add(i)
                    }
                }
            }
            else{
                Log.d(this.javaClass.name, "Error en getFreeMailboxes: $serverURL")
            }
        }
        return mailboxes
    }
}