package com.example.parcial2.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.parcial2.model.Car
import com.example.parcial2.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(car: Car): Long

    @Update
    suspend fun update(car: Car)

    @Query("DELETE FROM vehiculos WHERE vehiculoId = :carId")
    suspend fun deleteById(carId: Int): Int

    @Delete
    suspend fun delete(car: Car)

    @Query("SELECT * FROM vehiculos WHERE clienteId = :clientId")
    fun getCarsByClientId(clientId: Int): Flow<List<Car>>

    @Query("SELECT * FROM vehiculos")
    fun getAllCars(): Flow<List<Car>>

    @Query("SELECT * FROM vehiculos WHERE vehiculoId = :carId")
    fun getCarById(carId: Int): Flow<Car>
}