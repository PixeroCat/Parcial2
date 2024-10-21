package com.example.parcial2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clientes")
data class User(
    @PrimaryKey(autoGenerate = true)
    val clienteId: Int = 0,
    val nombre: String,
    val apellido: String,
    val telefono: String,
    val email: String,
    val direccion: String
)