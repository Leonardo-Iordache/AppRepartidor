package com.example.apprepartidor.mainScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.apprepartidor.R
import com.example.apprepartidor.iniciarsesion.LogInActivity
import com.example.apprepartidor.mqtt.MqttClient
import com.example.apprepartidor.items.Package as Paquete

class PackagesAdapter(private val listaPaquetes: ArrayList<Paquete>) :
    RecyclerView.Adapter<PackagesAdapter.PackagesViewHolder>() {

    class PackagesViewHolder(itemView: View) : ViewHolder(itemView) {
        private val mainScreen = MainScreenActivity()
        val idPaquete = itemView.findViewById<TextView>(R.id.package_text)
        val imagen = itemView.findViewById<ImageView>(R.id.package_image)
        val boton = itemView.findViewById<Button>(R.id.button_paquete)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackagesAdapter.PackagesViewHolder {
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

        val imageView = holder.imagen

        val button = holder.boton

        button.setOnClickListener{
            mqttClient.subscribe("arduino")
            mqttClient.publish("arduino", "a pitar")
        }

    }

    override fun getItemCount(): Int {
        return listaPaquetes.size
    }


}
