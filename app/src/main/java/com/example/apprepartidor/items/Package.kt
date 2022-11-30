package com.example.apprepartidor.items

import com.example.apprepartidor.items.Package as Paquete

class Package(
    val id: Int,
    val id_repartidor: Int,
    val direction: String,
) {
    companion object{
        private var lastPackageId = 0

        fun createPackageList(numPackage: Int): ArrayList<Paquete>{
            val paquetes = ArrayList<Paquete>()

            for(i in 1..numPackage){
                paquetes.add(Paquete(lastPackageId++, 1, "direccion generica"))
            }
            return paquetes
        }
    }
}