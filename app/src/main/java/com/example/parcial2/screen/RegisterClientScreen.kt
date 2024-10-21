package com.example.parcial2.screen

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
import androidx.navigation.NavHostController
import com.example.parcial2.model.User
import com.example.parcial2.repository.UserRepository
import kotlinx.coroutines.launch

@Composable
fun RegisterClientScreen(userRepository: UserRepository, navController: NavHostController) {

    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    // Focus requesters para pasar de campo con enter
    val focusRequesterApellido = remember { FocusRequester() }
    val focusRequesterTelefono = remember { FocusRequester() }
    val focusRequesterEmail = remember { FocusRequester() }
    val focusRequesterDireccion = remember { FocusRequester() }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "REGISTRO DE CLIENTE")

        Spacer(modifier = Modifier.height(16.dp))

        Card {

            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequesterApellido.requestFocus() }
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = apellido,
                onValueChange = { apellido = it },
                label = { Text("Apellido") },
                modifier = Modifier.focusRequester(focusRequesterApellido),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequesterTelefono.requestFocus() }
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text("Teléfono") },
                modifier = Modifier.focusRequester(focusRequesterTelefono),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequesterEmail.requestFocus() }
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.focusRequester(focusRequesterEmail),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequesterDireccion.requestFocus() }
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = direccion,
                onValueChange = { direccion = it },
                label = { Text("Dirección") },
                modifier = Modifier.focusRequester(focusRequesterDireccion),
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

        // Botón para registrar al cliente
        Button(onClick = {
            when {
                nombre.isEmpty() -> {
                    errorMessage = "El campo de nombre no puede estar vacío"
                }

                apellido.isEmpty() -> {
                    errorMessage = "El campo de apellido no puede estar vacío"
                }

                telefono.isEmpty() -> {
                    errorMessage = "El campo de telefono no puede estar vacío"
                }

                email.isEmpty() -> {
                    errorMessage = "El campo de email no puede estar vacío"
                }
//                direccion.isEmpty() -> {
//                    errorMessage = "El campo de direccion no puede estar vacío"
//                }

                else -> {
                    scope.launch {
                        val clienteId = userRepository.insert(
                            User(
                                nombre = nombre,
                                apellido = apellido,
                                telefono = telefono,
                                email = email,
                                direccion = direccion
                            )
                        )
                        errorMessage = ""
                        navController.navigate("registroVehiculo/$clienteId")
                    }
                }
            }
        }) {
            Text("Registrar Vehículo")
        }
    }
}
