package com.example.apprepartidor.mainScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.apprepartidor.R
import com.example.apprepartidor.items.Package as Paquete

class PackagesAdapter(private val listaPaquetes: ArrayList<Paquete>) :
    RecyclerView.Adapter<PackagesAdapter.PackagesViewHolder>() {

    class PackagesViewHolder(itemView: View) : ViewHolder(itemView) {
        private val mainScreen = MainScreenActivity()

        val idPaquete = itemView.findViewById<TextView>(R.id.package_text)
        val imagen = itemView.findViewById<ImageView>(R.id.package_image)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackagesAdapter.PackagesViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val packageView = inflater.inflate(R.layout.paquete_item, parent, false)

        return PackagesViewHolder(packageView)
    }

    override fun onBindViewHolder(holder: PackagesViewHolder, position: Int) {
        val paquete: Paquete = listaPaquetes.get(position)
        val textView = holder.idPaquete
        textView.text = paquete.id.toString()

        val imageView = holder.imagen

    }

    override fun getItemCount(): Int {
        return listaPaquetes.size
    }


}
