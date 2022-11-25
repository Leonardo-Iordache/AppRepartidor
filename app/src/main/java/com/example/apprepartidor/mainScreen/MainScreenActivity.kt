package com.example.apprepartidor.mainScreen

import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import android.os.Bundle
import com.example.apprepartidor.R
import com.example.apprepartidor.iniciarsesion.LogInActivity
import com.example.apprepartidor.databinding.ActivityMainScreenBinding


class MainScreenActivity : AppCompatActivity() {
    private val context = LogInActivity()
    private val mqttClient = context.getMQTT()
    private lateinit var packageID: String
    private lateinit var binding: ActivityMainScreenBinding
    private val packagesList = ArrayList<com.example.apprepartidor.items.Package>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        binding = ActivityMainScreenBinding.inflate(layoutInflater)

        binding.let{
        }
        setContentView(binding.root)
    }


}