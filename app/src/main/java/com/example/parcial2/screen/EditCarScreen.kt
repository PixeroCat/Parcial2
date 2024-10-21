package com.example.parcial2.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.parcial2.repository.CarRepository
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import kotlinx.coroutines.launch

@Composable
fun EditCarScreen(
    vehiculoId: Int,
    carRepository: CarRepository,
    navController: NavHostController
) {
    // Obtenemos el vehículo por su ID
    val vehiculo by carRepository.getCarById(vehiculoId).collectAsState(initial = null)

    vehiculo?.let { vehiculoActual ->
        var marca by remember { mutableStateOf(vehiculoActual.marca) }
        var modelo by remember { mutableStateOf(vehiculoActual.modelo) }
        var placa by remember { mutableStateOf(vehiculoActual.placa) }
        var color by remember { mutableStateOf(vehiculoActual.color) }
        var tipo by remember { mutableStateOf(vehiculoActual.tipo) }

        var errorMessage by remember { mutableStateOf("") }

        val scope = rememberCoroutineScope()

        // Focus requesters para pasar de campo con enter
        val focusRequesterModelo = remember { FocusRequester() }
        val focusRequesterPlaca = remember { FocusRequester() }
        val focusRequesterColor = remember { FocusRequester() }
        val focusRequesterTipo = remember { FocusRequester() }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "EDITAR VEHÍCULO")

            Spacer(modifier = Modifier.height(16.dp))

            Card {
                TextField(
                    value = marca,
                    onValueChange = { marca = it },
                    label = { Text("Marca") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequesterModelo.requestFocus() }
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = modelo,
                    onValueChange = { modelo = it },
                    label = { Text("Modelo") },
                    modifier = Modifier.focusRequester(focusRequesterModelo),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequesterPlaca.requestFocus() }
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = placa,
                    onValueChange = { placa = it },
                    label = { Text("Placa") },
                    modifier = Modifier.focusRequester(focusRequesterPlaca),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequesterColor.requestFocus() }
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = color,
                    onValueChange = { color = it },
                    label = { Text("Color") },
                    modifier = Modifier.focusRequester(focusRequesterColor),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequesterTipo.requestFocus() }
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = tipo,
                    onValueChange = { tipo = it },
                    label = { Text("Tipo") },
                    modifier = Modifier.focusRequester(focusRequesterTipo),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    )
                )

                // Mostrar mensaje de error si existe
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón para guardar los cambios en el vehículo
            Button(onClick = {
                when {
                    marca.isEmpty() -> errorMessage = "El campo de marca no puede estar vacío"
                    modelo.isEmpty() -> errorMessage = "El campo de modelo no puede estar vacío"
                    placa.isEmpty() -> errorMessage = "El campo de placa no puede estar vacío"
                    color.isEmpty() -> errorMessage = "El campo de color no puede estar vacío"
                    tipo.isEmpty() -> errorMessage = "El campo de tipo no puede estar vacío"
                    else -> {
                        scope.launch {
                            carRepository.update(
                                vehiculoActual.copy(
                                    marca = marca,
                                    modelo = modelo,
                                    placa = placa,
                                    color = color,
                                    tipo = tipo
                                )
                            )
                            errorMessage = ""
                            navController.popBackStack() // Volver a la pantalla anterior
                        }
                    }
                }
            }) {
                Text("Guardar Cambios")
            }
        }
    }
}





