package com.example.parcial2.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.parcial2.model.Service
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(service: Service): Long

    @Update
    suspend fun update(service: Service)

    @Delete
    suspend fun delete(service: Service)

    @Query("DELETE FROM servicios WHERE servicioId = :serviceId")
    suspend fun deleteById(serviceId: Int): Int

    @Query("SELECT * FROM servicios")
    fun getAllServices(): Flow<List<Service>>

    @Query("SELECT * FROM servicios WHERE servicioId = :serviceId")
    fun getServiceById(serviceId: Int): Flow<Service?>

}