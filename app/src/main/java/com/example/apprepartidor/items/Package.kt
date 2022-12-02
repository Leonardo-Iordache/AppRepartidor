package com.example.apprepartidor.items

import com.example.apprepartidor.items.Package as Paquete

data class Package(
    val id: Int,
    val id_repartidor: Int,
    val direction: String,
)