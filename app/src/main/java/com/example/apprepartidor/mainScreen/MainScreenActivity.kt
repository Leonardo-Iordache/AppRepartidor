package com.example.apprepartidor.mainScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apprepartidor.R
import com.example.apprepartidor.mqtt.MqttClient
import com.example.apprepartidor.responses.Mailbox
import com.example.apprepartidor.server.RestAPIService
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import com.example.apprepartidor.responses.Paquete
import org.eclipse.paho.client.mqttv3.*

class MainScreenActivity : AppCompatActivity() {
    private lateinit var paquetes: ArrayList<Paquete>
    private val apiService = RestAPIService()
    private var job: Job = Job()
    private lateinit var buzonesLibres: ArrayList<Mailbox>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        val recyclerViewPackage = findViewById<View>(R.id.recycler_viewPaquetes) as RecyclerView
        val buzonesTextView = findViewById<TextView>(R.id.buzonesLibresTextView)
        val userID = intent.extras?.getString("idUsuario")

        if (userID != null) {
            runBlocking {
                job = launch{
                    paquetes = apiService.getAllPackages(userID.toInt())
                    buzonesLibres = apiService.getFreeMailboxes()
                }
            }
        }
        job.invokeOnCompletion {
            var cadenaBuzones = ""
            for (i in buzonesLibres){
                Log.d(this.javaClass.name, i.idBuzon.toString())
                cadenaBuzones += i.idBuzon.toString() + "      "
            }
            Log.d(this.javaClass.name, "Cadena de buzones: $cadenaBuzones")
            buzonesTextView.text = cadenaBuzones
            Log.d(this.javaClass.name, "Paquetes obtenidos")
            val adapter = PackagesAdapter(paquetes)
            recyclerViewPackage.adapter = adapter
            recyclerViewPackage.layoutManager = LinearLayoutManager(this)
        }
    }
}
