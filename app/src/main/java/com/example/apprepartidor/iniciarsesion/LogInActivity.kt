package com.example.apprepartidor.iniciarsesion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.apprepartidor.R
import com.example.apprepartidor.databinding.ActivityIniciarSesionBinding
import com.example.apprepartidor.mainScreen.MainScreenActivity
import com.example.apprepartidor.server.RestAPIService
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIniciarSesionBinding
    private val apiService = RestAPIService()
    private lateinit var logInButton: Button
    private lateinit var userID: String
    private lateinit var userPassword: String
    private var job: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciar_sesion)
        binding = ActivityIniciarSesionBinding.inflate(layoutInflater)
        logInButton = binding.logInButtonIniciarSesionActivity
        logInButton.setOnClickListener {
            userID = binding.userID.text.toString()
            userPassword = binding.userPassword.text.toString()
            completeLogIn()
        }
        setContentView(binding.root)
    }

    private fun completeLogIn() {
        var userLogin = 0
        Log.d(this.javaClass.name, userID + userPassword)
        runBlocking {
            job = launch {
                userLogin = apiService.validateUser(userID, userPassword)
            }
        }
        job.invokeOnCompletion {
            val intent = Intent(this, MainScreenActivity::class.java)
            intent.putExtra("usuario", userLogin.toString())
            startActivity(intent)
        }
    }
}