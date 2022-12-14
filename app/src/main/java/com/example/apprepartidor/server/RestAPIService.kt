package com.example.apprepartidor.server


import android.util.Log
import com.example.apprepartidor.responses.Mailbox
import com.example.apprepartidor.responses.Paquete
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList


class RestAPIService {
    private val serverURL = "http://192.168.216.129:8080/"

    //registra un nuevo usuario
    suspend fun addUser(
        usuario: String,
        nombre: String,
        contrasena: String,
        apellidos: String,
        dni: String,
        empresa: String,
        id: Int
    ) {
        val retrofit = ServiceBuilder.buildService(ClientService::class.java)
        val call =
            retrofit.createNewUser(id, usuario, nombre, contrasena, apellidos, dni, empresa)
        val response = call.body()
        Log.d(this.javaClass.name, "AddUser: $response")
        if (response == 0) {
            Log.d(this.javaClass.name, "Correcto")
        } else {
            Log.d(this.javaClass.name, "Error en POST:${serverURL} $call")
        }
    }

    //valida las credenciales de un usuario
    suspend fun validateUser(userID: String, userPassword: String): Int {
        var id = 0
        val retrofit = ServiceBuilder.buildService(ClientService::class.java)
        val call = retrofit.validateUser(userID, userPassword)
        if (call.isSuccessful) {
            val items = call.body()
            id = items!!.id
            Log.d(this.javaClass.name, "El id nuevo es: $id")
        }
        return id
    }

    //funcion de prueba para un GET
    suspend fun getCode(idBuzon: String): Int{
        val retrofit = ServiceBuilder.buildService(ClientService::class.java)
        val call = retrofit.getCode(idBuzon)
        var codigo = 0;
        if (call.isSuccessful) {
            val response = call.body()
            codigo = response!!.codigo
            Log.d(this.javaClass.name, call.toString())
            Log.d(this.javaClass.name, response.toString())
        } else {
            Log.d(this.javaClass.name, "Error en POST: $serverURL")
        }
        return codigo
    }

    //obtiene todos los paquetes asociados a un ID
    suspend fun getAllPackages(id: Int): ArrayList<Paquete> {
        val paqueteTemporal: ArrayList<Paquete> = ArrayList()
        val retrofit = ServiceBuilder.buildService(ClientService::class.java)
        val call = retrofit.getPackages(id)
        if (call.isSuccessful) {
            val response = call.body()

            response?.let {
                for (i in it) {
                    Log.d(this.javaClass.name, i.toString())
                    paqueteTemporal.add(i)
                }
            }
        } else {
            Log.d(this.javaClass.name, "Error en getAllPackages: $serverURL")
        }
        return paqueteTemporal
    }

    suspend fun getFreeMailboxes(): ArrayList<Mailbox> {
        val mailboxes: ArrayList<Mailbox> = ArrayList()
        val retrofit = ServiceBuilder.buildService(ClientService::class.java)
        val call = retrofit.getFreeMailBoxes()
        if (call.isSuccessful) {
            val response = call.body()
            Log.d(this.javaClass.name, "Buzones: $response")
            response?.let {
                for (i in it) {
                    mailboxes.add(i)
                }
            }
        } else {
            Log.d(this.javaClass.name, "error en GetFreeMailBoxes: $serverURL")
        }
        return mailboxes
    }

    suspend fun deliverPackage(idPaquete: String, idBuzon: String) {
        val retrofit = ServiceBuilder.buildService(ClientService::class.java)
        val call = retrofit.deliverPackage(idPaquete, idBuzon)
        if (call.isSuccessful) {
            val response = call.body()
            if (response == 0) {
                Log.d(this.javaClass.name, "Paquete entregado correctamente")
            } else {
                Log.d(this.javaClass.name, "Error en deliverPackage:${serverURL} $call")
            }
        }
    }

    suspend fun useMailbox(idBuzon: String) {
        val retrofit = ServiceBuilder.buildService(ClientService::class.java)
        val call = retrofit.useMailbox(idBuzon)
        if (call.isSuccessful) {
            val response = call.body()
            if (response == 0) {
                Log.d(this.javaClass.name, "Buz??n actualizado correctamente")
            } else {
                Log.d(this.javaClass.name, "Error en use mail box:${serverURL} $call")
            }
        }
    }
}
