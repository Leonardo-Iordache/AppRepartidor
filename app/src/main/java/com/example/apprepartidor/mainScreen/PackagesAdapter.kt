package com.example.apprepartidor.mainScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.apprepartidor.R
import com.example.apprepartidor.mqtt.MqttClient
import com.example.apprepartidor.server.RestAPIService
import com.example.apprepartidor.items.Paquete as Paquete

class PackagesAdapter(private val listaPaquetes: ArrayList<Paquete>) :
    RecyclerView.Adapter<PackagesAdapter.PackagesViewHolder>() {
    private val apiService = RestAPIService()
    class PackagesViewHolder(itemView: View) : ViewHolder(itemView) {
        val idPaquete = itemView.findViewById<TextView>(R.id.package_text)
        val boton = itemView.findViewById<Button>(R.id.button_paquete)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackagesViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val packageView = inflater.inflate(R.layout.paquete_item, parent, false)
        return PackagesViewHolder(packageView)
    }

    override fun onBindViewHolder(holder: PackagesViewHolder, position: Int) {
        val contexto = MainScreenActivity()
        val mqttClient = MqttClient(contexto)

        val paquete: Paquete = listaPaquetes[position]
        val textView = holder.idPaquete
        textView.text = paquete.id.toString()
        val button = holder.boton

        button.setOnClickListener{
            while(!mqttClient.isConnected()){
                mqttClient.connect()
            }
            Toast.makeText(
                contexto.applicationContext, "Introducir el paquete en el buzon: ${asignMailbox()}", Toast.LENGTH_SHORT
            ).show()
            mqttClient.subscribe("arduino")
            mqttClient.publish("arduino", "paquete entregado")
        }

    }

    override fun getItemCount(): Int {
        return listaPaquetes.size
    }

    private fun asignMailbox(): Int{
        val freeMailboxes = apiService.getFreeMailboxes()
        return freeMailboxes[0].id
    }
}
