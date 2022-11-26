package com.example.apprepartidor

import android.util.Log
import android.widget.Toast
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient
import org.eclipse.paho.client.mqttv3.IMqttToken

data class User(
    var name: String,
    var lastName: String,
    var dni: String,
    var nEmployee: Int,
    var password: String,
) {




}