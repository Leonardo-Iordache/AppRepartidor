package com.example.apprepartidor.mainScreen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.apprepartidor.R
import com.example.apprepartidor.items.Package

class PackagesAdapter(
    context: Context,
    packageList: ArrayList<com.example.apprepartidor.items.Package>
) : RecyclerView.Adapter<PackagesAdapter.PackagesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackagesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PackagesViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class PackagesViewHolder(itemView: View, val onClick: (Package) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val mainScreen = MainScreenActivity()
        private var currentPackage: com.example.apprepartidor.items.Package? = null

        init {
            itemView.setOnClickListener {
                currentPackage?.let {
                    onClick(it)
                }
            }
        }
    }


}
