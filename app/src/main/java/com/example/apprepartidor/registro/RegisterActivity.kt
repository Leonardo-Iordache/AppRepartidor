package com.example.apprepartidor.registro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.apprepartidor.R
import com.example.apprepartidor.UserResponse
import com.example.apprepartidor.databinding.ActivityRegisterBinding
import com.example.apprepartidor.iniciarsesion.LogInActivity
import com.example.apprepartidor.server.ClientService
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var returnButton: Button
    private lateinit var registerButton: Button

    private lateinit var nameInput: EditText
    private lateinit var lastNameInput: EditText
    private lateinit var dniInput: EditText
    private lateinit var id: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText

    private val serverURL = "http://192.0.0.0/usuario/"
    private lateinit var outputJSON: String
    private lateinit var userResponse: UserResponse

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
            id = it.idText
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

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(serverURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun postNewUser(query: String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ClientService::class.java).putUserCredentials(query, outputJSON)
        }
    }

    private fun completeRegistration() {
        if (passwordInput.text.toString() == confirmPasswordInput.text.toString()) {
            userResponse = UserResponse(
                id.text.toString(),
                passwordInput.text.toString(),
                nameInput.text.toString(),
                lastNameInput.text.toString(),
                dniInput.text.toString()
            )
            outputJSON = Gson().toJson(userResponse)
            Log.i("Usuario", outputJSON.toString())

            postNewUser("http://...")
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }
    }
}