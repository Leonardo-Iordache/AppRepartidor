package com.example.apprepartidor

import android.content.Context
import com.example.apprepartidor.mqtt.MqttClient

object GlobalContext{
    private var mqttClient: MqttClient? = null

    fun withContext(contexto: Context){
        mqttClient = MqttClient(contexto)
    }

    fun getMqtt(): MqttClient?{
        return mqttClient
    }
}