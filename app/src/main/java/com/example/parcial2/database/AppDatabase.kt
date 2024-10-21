package com.example.parcial2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.parcial2.dao.CarDao
import com.example.parcial2.dao.RegisterDao
import com.example.parcial2.dao.ServiceDao
import com.example.parcial2.dao.UserDao
import com.example.parcial2.model.Car
import com.example.parcial2.model.Register
import com.example.parcial2.model.Service
import com.example.parcial2.model.User

@Database(entities = [User::class, Car::class, Service::class, Register::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun carDao(): CarDao
    abstract fun serviceDao(): ServiceDao
    abstract fun registerDao(): RegisterDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "lavado_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}