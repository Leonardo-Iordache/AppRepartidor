package com.example.apprepartidor.server

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.system.Os.read
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.*
import java.io.BufferedInputStream
import java.io.InputStream
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
}