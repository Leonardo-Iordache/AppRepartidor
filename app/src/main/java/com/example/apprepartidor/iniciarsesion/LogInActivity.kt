package com.example.apprepartidor.iniciarsesion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.apprepartidor.R
import com.example.apprepartidor.databinding.ActivityIniciarSesionBinding
import com.example.apprepartidor.mainScreen.MainScreenActivity
import com.example.apprepartidor.server.RestAPIService
import com.example.apprepartidor.items.Paquete as Paquete


class LogInActivity : AppCompatActivity() {
    private lateinit var logInButton: Button
    private lateinit var binding: ActivityIniciarSesionBinding
    private val apiService = RestAPIService()
    lateinit var userID: String
    private lateinit var userPassword: String
    private lateinit var paquetes: ArrayList<Paquete>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciar_sesion)
        binding = ActivityIniciarSesionBinding.inflate(layoutInflater)
        logInButton = binding.logInButtonIniciarSesionActivity
        logInButton.setOnClickListener {
            userID = binding.userID.toString()
            userPassword = binding.userPassword.toString()
            completeLogIn()
        }
        setContentView(binding.root)
    }

    private fun completeLogIn() {
        if(apiService.validateUser(userID.toInt(), userPassword)){
            val intent = Intent(this, MainScreenActivity::class.java)
            intent.putExtra("idUsuario", userID)
            startActivity(intent)
        }
        else{
            Toast.makeText(applicationContext, "Credenciales invalidas", Toast.LENGTH_SHORT).show()
        }

    }
}