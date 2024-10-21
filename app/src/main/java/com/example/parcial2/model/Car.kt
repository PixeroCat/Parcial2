package com.example.parcial2.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "vehiculos",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["clienteId"],
        childColumns = ["clienteId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Car(
    @PrimaryKey(autoGenerate = true)
    val vehiculoId: Int = 0,
    val clienteId: Int = 0,
    val marca: String,
    val modelo: String,
    val placa: String,
    val color: String,
    val tipo: String
)
