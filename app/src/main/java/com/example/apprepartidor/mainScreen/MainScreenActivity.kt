package com.example.apprepartidor.mainScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apprepartidor.R
import com.example.apprepartidor.server.RestAPIService
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import com.example.apprepartidor.responses.Paquete

class MainScreenActivity : AppCompatActivity() {
    private lateinit var paquetes: ArrayList<Paquete>
    private val apiService = RestAPIService()
    private var job: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        val recyclerViewPackage = findViewById<View>(R.id.recycler_viewPaquetes) as RecyclerView
        val userID = intent.extras?.getString("idUsuario")
        if (userID != null) {
            runBlocking {
                job = launch{
                    paquetes = apiService.getAllPackages(userID.toInt())
                }
            }
        }
        job.invokeOnCompletion {
            val adapter = PackagesAdapter(paquetes)
            recyclerViewPackage.adapter = adapter
            recyclerViewPackage.layoutManager = LinearLayoutManager(this)
        }
    }
}
