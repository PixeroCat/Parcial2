package com.example.parcial2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "servicios")
data class Service(
    @PrimaryKey(autoGenerate = true)
    val servicioId: Int = 0,
    val nombre: String,
    val precio: Double
)
