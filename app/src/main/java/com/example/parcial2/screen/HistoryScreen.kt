package com.example.parcial2.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.parcial2.repository.CarRepository
import com.example.parcial2.repository.ServiceRepository
import com.example.parcial2.repository.UserRepository

@Composable
fun HistoryScreen(
    navController: NavHostController,
    userRepository: UserRepository,
    carRepository: CarRepository,
    serviceRepository: ServiceRepository
) {

    val clientes by userRepository.getAllUsers().collectAsState(initial = emptyList())
    val vehiculos by carRepository.getAllCars().collectAsState(initial = emptyList())
    val servicios by serviceRepository.getAllServices().collectAsState(initial = emptyList())

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(clientes) { cliente ->
            val vehiculo = vehiculos.find { it.clienteId == cliente.clienteId }
            val servicio = vehiculo?.let { servicios.find { it.servicioId == vehiculo.vehiculoId } }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        // Navegar a la pantalla de info del historial con los datos seleccionados
                        navController.navigate(
                            "historialInfo/${cliente.clienteId}"
                        )
                    },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Cliente: ${cliente.nombre} ${cliente.apellido}")
                    vehiculo?.let {
                        Text(text = "Vehículo: ${it.marca} ${it.modelo}")
                    }
                    servicio?.let {
                        Text(text = "Servicio: ${it.nombre} - Precio: ${it.precio}")
                    }
                }
            }
        }
    }
}