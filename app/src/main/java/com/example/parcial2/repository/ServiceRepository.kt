package com.example.parcial2.repository

import com.example.parcial2.dao.ServiceDao
import com.example.parcial2.model.Service
import kotlinx.coroutines.flow.Flow

class ServiceRepository(private val serviceDao: ServiceDao) {

    suspend fun insert(service: Service): Long {
        return serviceDao.insert(service)
    }

    suspend fun update(service: Service) {
        serviceDao.update(service)
    }

    suspend fun deleteById(serviceId: Int): Int {
        return serviceDao.deleteById(serviceId)
    }

    suspend fun delete(service: Service) {
        serviceDao.delete(service)
    }

    fun getAllServices(): Flow<List<Service>> {
        return serviceDao.getAllServices()
    }

    fun getServiceById(serviceId: Int): Flow<Service?> {
        return serviceDao.getServiceById(serviceId)
    }

}