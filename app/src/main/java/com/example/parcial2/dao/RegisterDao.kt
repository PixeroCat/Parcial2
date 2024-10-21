package com.example.parcial2.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.parcial2.model.Register
import com.example.parcial2.model.Service
import kotlinx.coroutines.flow.Flow

@Dao
interface RegisterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(register: Register): Long

    @Update
    suspend fun update(register: Register)

    @Delete
    suspend fun delete(register: Register)

    @Query("DELETE FROM registrolavado WHERE registroId = :registerId")
    suspend fun deleteById(registerId: Int): Int

    @Query("SELECT * FROM registrolavado WHERE vehiculoId = :vehiculoId")
    fun getRegistersByVehicleId(vehiculoId: Int): Flow<List<Register>>

    @Query("SELECT * FROM registrolavado")
    fun getAllRegisters(): Flow<List<Register>>

    @Query("SELECT * FROM registrolavado WHERE registroId = :registerId")
    fun getRegisterById(registerId: Int): Flow<Register?>
}