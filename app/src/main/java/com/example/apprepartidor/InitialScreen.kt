package com.example.apprepartidor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.apprepartidor.databinding.ActivityMainBinding
import com.example.apprepartidor.iniciarsesion.LogInActivity
import com.example.apprepartidor.registro.RegisterActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class InitialScreen : AppCompatActivity() {
    private lateinit var logInButton: Button
    private lateinit var registerButton: Button
    private lateinit var binding: ActivityMainBinding

    val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.let {
            logInButton = it.logInButton
            registerButton = it.registerButton
        }

        logInButton.setOnClickListener {
            executeLogInActivity()
        }

        registerButton.setOnClickListener {
            executeRegisterActivity()
        }
        setContentView(binding.root)
    }


    private fun executeLogInActivity() {
        val intent = Intent(this, LogInActivity::class.java)
        startActivity(intent)
    }

    private fun executeRegisterActivity(){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}