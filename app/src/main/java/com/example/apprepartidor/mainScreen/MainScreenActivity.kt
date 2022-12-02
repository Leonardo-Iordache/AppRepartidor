package com.example.apprepartidor.mainScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apprepartidor.R
import com.example.apprepartidor.iniciarsesion.LogInActivity
import com.example.apprepartidor.server.RestAPIService
import com.example.apprepartidor.items.Package as Paquete

class MainScreenActivity : AppCompatActivity() {
    private lateinit var paquetes: ArrayList<Paquete>
    private val apiService = RestAPIService()
    private val currentID = LogInActivity().userID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        val recyclerViewPackage = findViewById<View>(R.id.recycler_viewPaquetes) as RecyclerView
        paquetes = apiService.getAllPackages(currentID)
        val adapter = PackagesAdapter(paquetes)
        recyclerViewPackage.adapter = adapter
        recyclerViewPackage.layoutManager = LinearLayoutManager(this)
    }
}
