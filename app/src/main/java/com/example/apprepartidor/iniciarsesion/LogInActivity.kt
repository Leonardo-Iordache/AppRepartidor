package com.example.apprepartidor.iniciarsesion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.apprepartidor.R
import com.example.apprepartidor.databinding.ActivityIniciarSesionBinding
import com.example.apprepartidor.mainScreen.MainScreenActivity
import com.example.apprepartidor.mqtt.MqttClient
import com.example.apprepartidor.server.ClientService
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.apprepartidor.items.Package as Paquete


class LogInActivity : AppCompatActivity() {
    private lateinit var logInButton: Button
    private lateinit var binding: ActivityIniciarSesionBinding
    private var mqttClient = MqttClient(this)
    private val serverURL = "http://192.168.1.129:8080/"

    private lateinit var userName: String
    private lateinit var userPassword: String

    private lateinit var paquetes: ArrayList<Paquete>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciar_sesion)

        binding = ActivityIniciarSesionBinding.inflate(layoutInflater)

        binding.let {
            logInButton = it.logInButtonIniciarSesionActivity
            userName = it.userName.toString()
            userPassword = it.userPassword.toString()
        }

        mqttClient.connect()


        logInButton.setOnClickListener {
            //searchUserByID()
            completeLogIn()
        /*if (mqttClient.isConnected()) {
                completeLogIn()
                //searchUserByID(serverURL)
            }*/
        }

        setContentView(binding.root)
    }


    private fun completeLogIn() {
        if(mqttClient.isConnected()){
            val topic = "arduino"

            mqttClient.subscribe(topic)
            mqttClient.publish(topic, "hola oscar desde el movil")

            val intent = Intent(this, MainScreenActivity::class.java)
            startActivity(intent)
        }
    }


    private fun getRetrofit(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl(serverURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


    private fun searchUserByID() {
        CoroutineScope(Dispatchers.IO).launch {
            val call =
                getRetrofit().create(ClientService::class.java).getUserByID()
            val user = call.body()

            if (call.isSuccessful) {
                val name = (user?.name ?: String) as String
                val contrasena = (user?.password ?: String) as String

                /*if (name == userName && contrasena == userPassword){
                    completeLogIn()
                }*/
                //completeLogIn()
            } else {
                Log.d(this.javaClass.name, "Error al conectar con el servidor:${serverURL}")
            }
        }
    }

    /*private fun searchPackage(query: String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ClientService::class.java).getPackageByID("$query/id")
            val paquete = call.body()

            if(call.isSuccessful){
                val paqueteObtenido =
            }
        }
    }*/


}