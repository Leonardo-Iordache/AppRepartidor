package com.example.apprepartidor.iniciarsesion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.apprepartidor.R
import com.example.apprepartidor.databinding.ActivityIniciarSesionBinding
import com.example.apprepartidor.mainScreen.MainScreenActivity
import com.example.apprepartidor.mqtt.MqttClient
import com.example.apprepartidor.server.RestAPIService
import com.example.apprepartidor.items.Package as Paquete


class LogInActivity : AppCompatActivity() {
    private lateinit var logInButton: Button
    private lateinit var binding: ActivityIniciarSesionBinding
    private var mqttClient = MqttClient(this)
    private val apiService = RestAPIService()
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
}