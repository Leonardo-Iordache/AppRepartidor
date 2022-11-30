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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory;

class LogInActivity : AppCompatActivity() {
    private lateinit var logInButton: Button
    private lateinit var binding: ActivityIniciarSesionBinding
    private var mqttClient = MqttClient(this)
    private val serverURL = "http://192.0.0.0/usuario/"

    private lateinit var userName: String
    private lateinit var userPassword: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciar_sesion)

        binding = ActivityIniciarSesionBinding.inflate(layoutInflater)

        binding.let{
            logInButton = it.logInButtonIniciarSesionActivity
            userName = it.userName.toString()
            userPassword = it.userPassword.toString()
        }

        logInButton.setOnClickListener {
            mqttClient.connect("android", "1234")

            if(mqttClient.isConnected()){
                searchByID("http://")
            }
        }

        setContentView(binding.root)
    }

    private fun completeLogIn(){
        val topic = "test"

        mqttClient.subscribe(topic)
        mqttClient.publish(topic, "hola")
        val intent = Intent(this, MainScreenActivity::class.java)
        startActivity(intent)
    }

    fun getMQTT(): MqttClient{
        return this.mqttClient
    }

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(serverURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchByID(query: String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ClientService::class.java).getUserByID("$query/id")
            val user = call.body()

            if(call.isSuccessful){
                val name = (user?.name?: String) as String
                val contrasena = (user?.password?: String) as String

                if (name == userName && contrasena == userPassword){
                    completeLogIn()
                }
            }
            else{
                Log.d(this.javaClass.name, "Error al conectar con el servidor:${serverURL}")
            }
        }
    }








}