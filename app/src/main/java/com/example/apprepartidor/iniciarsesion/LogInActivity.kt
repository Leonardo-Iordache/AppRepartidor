package com.example.apprepartidor.iniciarsesion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.apprepartidor.R
import com.example.apprepartidor.databinding.ActivityIniciarSesionBinding
import com.example.apprepartidor.mainScreen.MainScreenActivity
import com.example.apprepartidor.mqtt.MqttClient

class LogInActivity : AppCompatActivity() {
    private lateinit var logInButton: Button
    private lateinit var binding: ActivityIniciarSesionBinding
    private var mqttClient = MqttClient(this)

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
            //TODO: llamar a funcion de conectarse a mqtt
            mqttClient.connect("android", "1234")
            if(mqttClient.isConnected()){
                completeLogIn()
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








}