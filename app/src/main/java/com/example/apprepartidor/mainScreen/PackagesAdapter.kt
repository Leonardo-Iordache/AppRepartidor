package com.example.apprepartidor.mainScreen

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.apprepartidor.GlobalContext
import com.example.apprepartidor.R
import com.example.apprepartidor.iniciarsesion.LogInActivity
import com.example.apprepartidor.mqtt.MqttClient
import com.example.apprepartidor.responses.Mailbox
import com.example.apprepartidor.server.RestAPIService
import com.example.apprepartidor.responses.Paquete
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class PackagesAdapter(private val listaPaquetes: ArrayList<Paquete>) :
    RecyclerView.Adapter<PackagesAdapter.PackagesViewHolder>() {
    private val apiService = RestAPIService()
    class PackagesViewHolder(itemView: View) : ViewHolder(itemView) {
        val idPaquete = itemView.findViewById<TextView>(R.id.package_text)
        val buzonEntrega = itemView.findViewById<TextView>(R.id.buzonEntregaText)
        val boton = itemView.findViewById<Button>(R.id.button_paquete)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackagesViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val packageView = inflater.inflate(R.layout.paquete_item, parent, false)
        return PackagesViewHolder(packageView)
    }

    override fun onBindViewHolder(holder: PackagesViewHolder, position: Int) {
        val paquete: Paquete = listaPaquetes[position]
        val textView = holder.idPaquete
        val buzonEntrega = holder.buzonEntrega
        textView.text = paquete.idPaquete.toString()
        val button = holder.boton
        asignMailbox(buzonEntrega)

        button.setOnClickListener{
            sendData(paquete.idPaquete.toString(), buzonEntrega.text.toString())
            GlobalContext.getMqtt()?.publish("buzon/entregas", buzonEntrega.text.toString())
        }

    }

    override fun getItemCount(): Int {
        return listaPaquetes.size
    }

    private fun asignMailbox(buzonEntrega: TextView) = runBlocking{
        val job: Job
        var freeMailboxes: ArrayList<Mailbox> = ArrayList()

        job = launch{
            freeMailboxes = apiService.getFreeMailboxes()
            Log.d(this.javaClass.name, "Buzones libres: $freeMailboxes")
        }

        job.join()
        buzonEntrega.text = freeMailboxes[0].idBuzon.toString()
    }

    private fun sendData(idPaquete: String, idBuzon: String) = runBlocking{
        val job: Job = launch{
            apiService.deliverPackage(idPaquete, idBuzon)
            apiService.useMailbox(idBuzon)
            val codigo = apiService.getCode(idPaquete).toString()
            Log.d(this.javaClass.name, "El codigo del buzón es: $codigo")
            GlobalContext.getMqtt()?.publish("buzon/codigo", codigo)
        }
        job.join()
    }

    /*Toast.makeText(
    contexto.applicationContext, "Introducir el paquete en el buzon: ${freeMailboxes[0].id}", Toast.LENGTH_SHORT
    ).show()*/
}
