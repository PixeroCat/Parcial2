package com.example.parcial2.repository

import com.example.parcial2.dao.CarDao
import com.example.parcial2.model.Car
import com.example.parcial2.model.User
import kotlinx.coroutines.flow.Flow

class CarRepository(private val carDao: CarDao){

    suspend fun insert(car: Car): Long {
        return carDao.insert(car)
    }

    suspend fun update(car: Car){
        carDao.update(car)
    }

    suspend fun deleteById(carId: Int): Int {
        return carDao.deleteById(carId)
    }

    suspend fun delete(car: Car){
        return carDao.delete(car)
    }

    fun getCarsByClient(clientId: Int): Flow<List<Car>> {
        return carDao.getCarsByClientId(clientId)
    }

    fun getAllCars(): Flow<List<Car>> {
        return carDao.getAllCars()
    }

    fun getCarById(carId: Int): Flow<Car?> {
        return carDao.getCarById(carId)
    }

}