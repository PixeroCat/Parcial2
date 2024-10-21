package com.example.parcial2.repository

import com.example.parcial2.dao.RegisterDao
import com.example.parcial2.model.Register
import com.example.parcial2.model.Service
import kotlinx.coroutines.flow.Flow

class RegisterRepository(private val registerDao: RegisterDao) {

    suspend fun insert(register: Register): Long {
        return registerDao.insert(register)
    }

    suspend fun update(register: Register) {
        registerDao.update(register)
    }

    fun getRegistersByVehicleId(vehiculoId: Int): Flow<List<Register>> {
        return registerDao.getRegistersByVehicleId(vehiculoId)  // Devuelve un Flow para observar los registros de un vehículo
    }

    fun getAllRegisters(): Flow<List<Register>> {
        return registerDao.getAllRegisters()  // Devuelve un Flow para observar todos los registros
    }

    suspend fun deleteById(registerId: Int): Int {
        return registerDao.deleteById(registerId)
    }

    suspend fun delete(register: Register) {
        registerDao.delete(register)
    }

    fun getRegisterById(registerId: Int): Flow<Register?> {
        return registerDao.getRegisterById(registerId)
    }
}