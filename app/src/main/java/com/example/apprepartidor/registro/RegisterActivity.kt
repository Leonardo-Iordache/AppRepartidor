package com.example.apprepartidor.registro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.apprepartidor.R
import com.example.apprepartidor.User
import com.example.apprepartidor.databinding.ActivityRegisterBinding
import com.example.apprepartidor.iniciarsesion.LogInActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var returnButton: Button
    private lateinit var registerButton: Button

    private lateinit var nameInput: EditText
    private lateinit var lastNameInput: EditText
    private lateinit var dniInput: EditText
    private lateinit var nEmployee: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        binding = ActivityRegisterBinding.inflate(layoutInflater)

        binding.let {
            returnButton = it.returnButton
            registerButton = it.registerButton
            nameInput = it.nameText
            lastNameInput = it.lastNameText
            dniInput = it.dniText
            nEmployee = it.nEmployeeText
            passwordInput = it.password
            confirmPasswordInput = it.confirmPassword
        }

        returnButton.setOnClickListener {
            returnBack()
        }

        registerButton.setOnClickListener {
            completeRegistration()
        }

        setContentView(binding.root)
    }


    private fun returnBack() {
        finish()
    }

    private fun completeRegistration() {
        if (binding.password.toString() == binding.confirmPassword.toString()){
            val user = User(binding.nameText.toString(),
                binding.lastNameText.toString(),
                binding.dniText.toString(),
                binding.nEmployeeText.toString().toInt(),
                binding.password.toString())
        }
        val intent = Intent(this, LogInActivity::class.java)
        startActivity(intent)

    }
}