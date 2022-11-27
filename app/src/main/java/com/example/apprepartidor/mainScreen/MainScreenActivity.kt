package com.example.apprepartidor.mainScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apprepartidor.R
import com.example.apprepartidor.iniciarsesion.LogInActivity
import com.example.apprepartidor.databinding.ActivityMainScreenBinding
import com.example.apprepartidor.items.Package as Paquete

class MainScreenActivity : AppCompatActivity() {
    private val context = LogInActivity()
    private val mqttClient = context.getMQTT()
    lateinit var paquetes: ArrayList<Paquete>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        val recyclerViewPackage = findViewById<View>(R.id.recycler_viewPaquetes) as RecyclerView

        paquetes = Paquete.createPackageList(10)

        val adapter = PackagesAdapter(paquetes)

        recyclerViewPackage.adapter = adapter
        recyclerViewPackage.layoutManager = LinearLayoutManager(this)

    }

}
