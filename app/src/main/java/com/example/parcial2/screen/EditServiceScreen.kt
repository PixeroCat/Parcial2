package com.example.parcial2.screen

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.example.parcial2.repository.ServiceRepository
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.parcial2.model.Register
import com.example.parcial2.repository.RegisterRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun EditServiceScreen(
    servicioId: Int,
    registroId: Int,
    serviceRepository: ServiceRepository,
    navController: NavHostController,
    registerRepository: RegisterRepository
) {
    // Obtenemos el servicio por su ID
    val servicio by serviceRepository.getServiceById(servicioId).collectAsState(initial = null)
    val registro by registerRepository.getRegisterById(registroId).collectAsState(initial = null)

    // Validamos si ya tenemos el servicio y el registro cargado
    servicio?.let { servicioActual ->
        registro?.let { registroActual ->

            // Inicializamos las variables con los datos actuales del servicio y registro
            var nombre by remember { mutableStateOf(servicioActual.nombre) }
            var precio by remember { mutableStateOf(servicioActual.precio.toString()) }
            var fechaLavado by remember { mutableStateOf(registroActual.fechaLavado) }
            var horaInicio by remember { mutableStateOf(registroActual.horaInicio) }
            var horaFin by remember { mutableStateOf(registroActual.horaFin) }
            var precioTotal by remember { mutableStateOf(registroActual.precioTotal.toString()) }

            var errorMessage by remember { mutableStateOf("") }

            val scope = rememberCoroutineScope()
            val context = LocalContext.current
            val calendar = Calendar.getInstance()

            // Focus requesters para pasar de campo con enter
            val focusRequesterPrecio = remember { FocusRequester() }
            val focusRequesterPrecioTotal = remember { FocusRequester() }

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

            // DatePickerDialog para seleccionar la fecha de lavado
            val datePickerDialog = DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    fechaLavado = dateFormat.format(calendar.time)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            // TimePickerDialog para seleccionar la hora de inicio
            val timePickerDialogInicio = TimePickerDialog(
                context,
                { _, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    horaInicio = timeFormat.format(calendar.time)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )

            // TimePickerDialog para seleccionar la hora de fin
            val timePickerDialogFin = TimePickerDialog(
                context,
                { _, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    horaFin = timeFormat.format(calendar.time)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "EDITAR SERVICIO")

                Spacer(modifier = Modifier.height(16.dp))

                Card {

                    TextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Tipo de Servicio") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusRequesterPrecio.requestFocus() }
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = precio,
                        onValueChange = { precio = it },
                        label = { Text("Precio") },
                        modifier = Modifier.focusRequester(focusRequesterPrecio),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Botón para mostrar el selector de fecha
                    Button(onClick = { datePickerDialog.show() }) {
                        Text(if (fechaLavado.isEmpty()) "Seleccionar Fecha de Lavado" else "Fecha de Lavado: $fechaLavado")
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    // Botón para mostrar el selector de hora de inicio
                    Button(onClick = { timePickerDialogInicio.show() }) {
                        Text(if (horaInicio.isEmpty()) "Seleccionar Hora de Inicio" else "Hora de Inicio: $horaInicio")
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    // Botón para mostrar el selector de hora de fin
                    Button(onClick = { timePickerDialogFin.show() }) {
                        Text(if (horaFin.isEmpty()) "Seleccionar Hora de Fin" else "Hora de Fin: $horaFin")
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = precioTotal,
                        onValueChange = { precioTotal = it },
                        label = { Text("Precio Total") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.focusRequester(focusRequesterPrecioTotal)
                    )

                    // Mostrar mensaje de error si algún campo está vacío
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

                // Botón para guardar los cambios
                Button(onClick = {
                    // Validar si todos los campos están llenos
                    when {
                        nombre.isEmpty() -> errorMessage = "El campo de tipo de servicio no puede estar vacío"
                        precio.isEmpty() -> errorMessage = "El campo de precio no puede estar vacío"
                        fechaLavado.isEmpty() -> errorMessage = "El campo de fecha de lavado no puede estar vacío"
                        horaInicio.isEmpty() -> errorMessage = "El campo de hora de inicio no puede estar vacío"
                        horaFin.isEmpty() -> errorMessage = "El campo de hora de fin no puede estar vacío"
                        precioTotal.isEmpty() -> errorMessage = "El campo de precio total no puede estar vacío"
                        else -> {
                            // Si todos los campos están llenos, proceder con la actualización del servicio
                            scope.launch {
                                serviceRepository.update(
                                    servicioActual.copy(
                                        nombre = nombre,
                                        precio = precio.toDouble()
                                    )
                                )
                                registerRepository.update(
                                    registroActual.copy(
                                        fechaLavado = fechaLavado,
                                        horaInicio = horaInicio,
                                        horaFin = horaFin,
                                        precioTotal = precioTotal.toDouble()
                                    )
                                )
                                errorMessage = ""
                                navController.navigate("historial")
                            }
                        }
                    }
                }) {
                    Text("Guardar")
                }
            }
        }
    }
}

