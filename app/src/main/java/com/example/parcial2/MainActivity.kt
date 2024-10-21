package com.example.parcial2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.parcial2.dao.CarDao
import com.example.parcial2.dao.RegisterDao
import com.example.parcial2.dao.ServiceDao
import com.example.parcial2.dao.UserDao
import com.example.parcial2.database.AppDatabase
import com.example.parcial2.navigation.AppNavigation
import com.example.parcial2.repository.CarRepository
import com.example.parcial2.repository.RegisterRepository
import com.example.parcial2.repository.ServiceRepository
import com.example.parcial2.repository.UserRepository
import com.example.parcial2.ui.theme.Parcial2Theme

class MainActivity : ComponentActivity() {

    // Inicializar DAO
    private lateinit var CarDao: CarDao
    private lateinit var RegisterDao: RegisterDao
    private lateinit var ServiceDao: ServiceDao
    private lateinit var UserDao: UserDao
    // Inicializar Repositorios
    private lateinit var CarRepository: CarRepository
    private lateinit var RegisterRepository: RegisterRepository
    private lateinit var ServiceRepository: ServiceRepository
    private lateinit var UserRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getDatabase(applicationContext)
        // Daos
        UserDao = db.userDao()
        CarDao = db.carDao()
        ServiceDao = db.serviceDao()
        RegisterDao = db.registerDao()
        //Repositorios
        UserRepository = UserRepository(UserDao)
        CarRepository = CarRepository(CarDao)
        ServiceRepository = ServiceRepository(ServiceDao)
        RegisterRepository = RegisterRepository(RegisterDao)

        val currentUserId = 1 // Reemplaza con el ID del usuario actual

        enableEdgeToEdge()
        setContent {
            Parcial2Theme {
                AppNavigation(
                    UserRepository = UserRepository,
                    CarRepository = CarRepository,
                    ServiceRepository = ServiceRepository,
                    RegisterRepository = RegisterRepository
                )
            }
        }
    }
}

