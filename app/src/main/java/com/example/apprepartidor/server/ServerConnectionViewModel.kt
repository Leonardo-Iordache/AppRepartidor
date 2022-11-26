package com.example.apprepartidor.server

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


class ServerConnectionViewModel(application: Application) : AndroidViewModel(application) {
    private val viewModelScope = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelScope)
    private val context = getApplication<Application>()

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    fun connectToServer() {
        uiScope.launch {
            if (checkConnection(context)) {
                connect()
            } else {
                //TODO: mensaje de error
            }
            //TODO: modify UI
        }
    }

    //
    private suspend fun checkConnection(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    private suspend fun connect() = withContext(Dispatchers.IO) {
        val url = URL("http://www.android.com/") //TODO: incluir url del server
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            val data = urlConnection.inputStream.bufferedReader().use { it.readText() } //Tratar los datos
        } finally {
            urlConnection.disconnect()
        }
    }


    private suspend fun rawJSON() = withContext(Dispatchers.IO){

        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("name", "Jack")
        jsonObject.put("salary", "3540")
        jsonObject.put("age", "23")

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        uiScope.launch(Dispatchers.IO) {
            val url = URL("http://dummy.restapiexample.com/api/v1/create")
            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "POST"
            httpURLConnection.setRequestProperty("Content-Type", "application/json") // The format of the content we're sending to the server
            httpURLConnection.setRequestProperty("Accept", "application/json") // The format of response we want to get from the server
            httpURLConnection.doInput = true
            httpURLConnection.doOutput = true

            // Send the JSON we created
            val outputStreamWriter = OutputStreamWriter(httpURLConnection.outputStream)
            outputStreamWriter.write(jsonObjectString)
            outputStreamWriter.flush()

            // Check if the connection is successful
            val responseCode = httpURLConnection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = httpURLConnection.inputStream.bufferedReader()
                    .use { it.readText() }  // defaults to UTF-8
                withContext(Dispatchers.Main) {

                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(JsonParser.parseString(response))
                    Log.d("Pretty Printed JSON :", prettyJson)

                }
            } else {
                Log.e("HTTPURLCONNECTION_ERROR", responseCode.toString())
            }
        }
    }
}
















