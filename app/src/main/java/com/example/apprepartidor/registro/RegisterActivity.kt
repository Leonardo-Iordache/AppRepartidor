package com.example.apprepartidor.registro

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.apprepartidor.R
import com.example.apprepartidor.UserResponse
import com.example.apprepartidor.databinding.ActivityRegisterBinding
import com.example.apprepartidor.iniciarsesion.LogInActivity
import com.example.apprepartidor.server.RestAPIService
import com.google.gson.Gson

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var returnButton: Button
    private lateinit var registerButton: Button
    private lateinit var nameInput: String
    private lateinit var lastNameInput: String
    private lateinit var dniInput: String
    private lateinit var id: String
    private lateinit var passwordInput: String
    private lateinit var confirmPasswordInput: String
    private val apiService = RestAPIService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        binding.let {
            returnButton = it.returnButton
            registerButton = it.registerButton
        }

        returnButton.setOnClickListener {
            finish()
        }

        registerButton.setOnClickListener {
            binding.let {
                nameInput = it.nameText.text.toString()
                lastNameInput = it.lastNameText.text.toString()
                dniInput = it.dniText.text.toString()
                id = it.idText.text.toString()
                passwordInput = it.password.text.toString()
                confirmPasswordInput = it.confirmPassword.text.toString()
            }
            completeRegistration()
        }
        setContentView(binding.root)
    }


    private fun completeRegistration() {
        if (passwordInput == confirmPasswordInput) {
            val userResponse = UserResponse(
                id, passwordInput, nameInput, lastNameInput, dniInput
            )
            apiService.addUser(userResponse, applicationContext)
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)

        } else {
            Toast.makeText(
                applicationContext, "Las contraseñas deben coincidir", Toast.LENGTH_SHORT
            ).show()
        }
    }
}