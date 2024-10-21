package com.example.parcial2.screen

import androidx.navigation.NavHostController
import com.example.parcial2.repository.CarRepository
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.parcial2.model.Car
import kotlinx.coroutines.launch

@Composable
fun RegisterCarScreen(
    clienteId: Int,
    carRepository: CarRepository,
    navController: NavHostController
) {

    var marca by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var placa by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    // Focus requesters para pasar de campo con enter
    val focusRequesterModelo = remember { FocusRequester() }
    val focusRequesterPlaca = remember { FocusRequester() }
    val focusRequesterColor = remember { FocusRequester() }
    val focusRequesterTipo = remember { FocusRequester() }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

            Text(text = "REGISTRO DE VEHÍCULO")

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

            TextField(value = color,
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

//            Spacer(modifier = Modifier.height(16.dp))

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

        // Botón para registrar el vehículo
        Button(onClick = {
            when {
                marca.isEmpty() -> {
                    errorMessage = "El campo de marca no puede estar vacío"
                }

                modelo.isEmpty() -> {
                    errorMessage = "El campo de modelo no puede estar vacío"
                }

                placa.isEmpty() -> {
                    errorMessage = "El campo de placa no puede estar vacío"
                }

                color.isEmpty() -> {
                    errorMessage = "El campo de color no puede estar vacío"
                }

                tipo.isEmpty() -> {
                    errorMessage = "El campo de tipo no puede estar vacío"
                }

                else -> {
                    scope.launch {
                        val vehiculoId = carRepository.insert(
                            Car(
                                clienteId = clienteId,
                                marca = marca,
                                modelo = modelo,
                                placa = placa,
                                color = color,
                                tipo = tipo
                            )
                        )
                        errorMessage = ""
                        navController.navigate("registroServicio/$vehiculoId")
                    }
                }
            }

        }) {
            Text("Registrar Servicio")
        }

    }
}