package com.example.parcial2.repository

import com.example.parcial2.dao.UserDao
import com.example.parcial2.model.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    suspend fun insert(user: User): Long {
        return userDao.insert(user)
    }

    suspend fun update(user: User) {
        userDao.update(user)
    }

    fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers()
    }

    suspend fun deleteById(userId: Int): Int {
        return userDao.deleteById(userId)
    }

    suspend fun delete(user: User) {
        userDao.delete(user)
    }

    fun getUserById(userId: Int): Flow<User> {
        return userDao.getUserById(userId)
    }
}