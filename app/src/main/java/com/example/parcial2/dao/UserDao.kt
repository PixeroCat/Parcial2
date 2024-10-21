package com.example.parcial2.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.parcial2.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User): Long

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("DELETE FROM clientes WHERE clienteId = :userId")
    suspend fun deleteById(userId: Int): Int

    @Query("SELECT * FROM clientes")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM clientes WHERE clienteId = :userId")
    fun getUserById(userId: Int): Flow<User>

}