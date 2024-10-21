import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.parcial2.model.Register
import com.example.parcial2.model.Service
import com.example.parcial2.repository.RegisterRepository
import com.example.parcial2.repository.ServiceRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun RegisterServiceScreen(
    vehiculoId: Int,
    serviceRepository: ServiceRepository,
    navController: NavHostController,
    registerRepository: RegisterRepository
) {
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var fechaLavado by remember { mutableStateOf("") }
    var horaInicio by remember { mutableStateOf("") }
    var horaFin by remember { mutableStateOf("") }
    var precioTotal by remember { mutableStateOf("") }

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

        Text(text = "REGISTRO DE SERVICIO")

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
//            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar mensaje de error si un campo esta vacio
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
        // Botón para registrar el servicio y el registro de lavado
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
                    // Si todos los campos están llenos, proceder con el registro
                    scope.launch {
                        val serviceId = serviceRepository.insert(
                            Service(
                                nombre = nombre,
                                precio = precio.toDouble()
                            )
                        )
                        registerRepository.insert(
                            Register(
                                vehiculoId = vehiculoId,
                                servicioId = serviceId.toInt(),
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
            Text("Registrar y Finalizar")
        }
    }
}
