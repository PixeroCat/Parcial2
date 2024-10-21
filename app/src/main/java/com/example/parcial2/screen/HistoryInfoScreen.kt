package com.example.parcial2.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.parcial2.model.Register
import com.example.parcial2.model.Service
import com.example.parcial2.model.User
import com.example.parcial2.repository.CarRepository
import com.example.parcial2.repository.RegisterRepository
import com.example.parcial2.repository.ServiceRepository
import com.example.parcial2.repository.UserRepository
import kotlinx.coroutines.launch

@Composable
fun HistoryInfoScreen(
    clienteId: Int,
    userRepository: UserRepository,
    carRepository: CarRepository,
    registerRepository: RegisterRepository,
    serviceRepository: ServiceRepository,
    navController: NavHostController
) {

    // Obtenemos el cliente por su clienteId
    val cliente by userRepository.getUserById(clienteId).collectAsState(initial = null)

    // Obtenemos todos los vehículos asociados al clienteId
    val vehiculos by carRepository.getCarsByClient(clienteId).collectAsState(initial = emptyList())

    // Lista para los registros de lavado (relaciona vehículos y servicios)
    val registros = remember { mutableStateListOf<Register?>() }

    // Pantalla de confirmación de eliminación
    var mostrarEliminado by remember { mutableStateOf(false) }
    var vehiculoSeleccionado by remember { mutableStateOf(0) } // Guardar el ID del vehículo seleccionado
    var servicioSeleccionado by remember { mutableStateOf(0) } // Guardar el ID del servicio seleccionado
    var registroSeleccionado by remember { mutableStateOf(0) } // Guardar el ID del registro seleccionado

    // Actualizamos la lista de registros y sus servicios relacionados cada vez que cambian los vehículos
    LaunchedEffect(vehiculos) {
        registros.clear()
        vehiculos.forEach { vehiculo ->
            val registroFlow = registerRepository.getRegistersByVehicleId(vehiculo.vehiculoId)
            registroFlow.collect { registro ->
                registros.add(registro?.firstOrNull())
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        cliente?.let {
            OutlinedTextField(
                value = "${it.nombre} ${it.apellido}",
                onValueChange = {}, // No permite cambios
                readOnly = true, // Modo solo lectura
                label = { Text("Cliente") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Mostrar los vehículos y registros relacionados
        vehiculos.forEachIndexed { index, vehiculo ->
            OutlinedTextField(
                value = "${vehiculo.marca} ${vehiculo.modelo}",
                onValueChange = {},  // No permite cambios
                readOnly = true,  // Modo solo lectura
                label = { Text("Vehículo") },
                modifier = Modifier.fillMaxWidth()
            )

            // Obtener el registro de lavado
            registros.getOrNull(index)?.let { registro ->
                val servicio by serviceRepository.getServiceById(registro.servicioId)
                    .collectAsState(initial = null)

                servicio?.let {
                    OutlinedTextField(
                        value = "${it.nombre}",
                        onValueChange = {}, // No permite cambios
                        readOnly = true, // Modo solo lectura
                        label = { Text("Servicio") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = "${it.precio}",
                        onValueChange = {}, // No permite cambios
                        readOnly = true, // Modo solo lectura
                        label = { Text("Precio") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = "${registro.horaInicio}",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Hora de inicio") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = "${registro.horaFin}",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Hora final") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = "${registro.precioTotal}",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Precio total") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Guardamos los IDs del vehículo y servicio seleccionados para editar o eliminar
                    vehiculoSeleccionado = vehiculo.vehiculoId
                    servicioSeleccionado = it.servicioId
                    registroSeleccionado = registro.registroId
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Botón de editar
            Button(
                onClick = {
                    navController.navigate("editarTabs/$clienteId/$vehiculoSeleccionado/$servicioSeleccionado/$registroSeleccionado")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2596be),
                )
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Editar")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Editar")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón de eliminar
            Button(
                onClick = {
                    mostrarEliminado = true // Mostramos el diálogo de confirmación
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFde2c26),
                )
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Eliminar")
            }
        }
    }

    // Diálogo de confirmación para eliminar el registro
    if (mostrarEliminado) {
        AlertDialog(
            onDismissRequest = { mostrarEliminado = false },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro de que quieres eliminar este registro, junto con los vehículos y servicios relacionados?") },
            confirmButton = {
                val scope = rememberCoroutineScope()
                TextButton(
                    onClick = {
                        cliente?.let {
                            scope.launch {
                                // Eliminar el usuario y los registros relacionados (vehículos y servicios)
                                userRepository.delete(it)
                            }
                        }
                        mostrarEliminado = false
                        // Navegar hacia atrás después de eliminar
                        navController.navigate("historial")
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarEliminado = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}




