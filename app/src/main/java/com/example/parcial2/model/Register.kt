package com.example.parcial2.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "registrolavado",
    foreignKeys = [
        ForeignKey(
            entity = Car::class,
            parentColumns = ["vehiculoId"],
            childColumns = ["vehiculoId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Service::class,
            parentColumns = ["servicioId"],
            childColumns = ["servicioId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Register(
    @PrimaryKey(autoGenerate = true)
    val registroId: Int = 0,
    val vehiculoId: Int,
    val servicioId: Int,
    val fechaLavado: String,
    val horaInicio: String,
    val horaFin: String,
    val precioTotal: Double
)
