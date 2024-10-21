package com.example.parcial2.scaffold

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScaffold(navController: NavHostController, content: @Composable () -> Unit) {

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Registro")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                BottomNavigationMenu(navController)
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // Aquí se muestra el contenido de la pantalla actual
                content()
            }
        }
    )
}

// Composable para la barra inferior
@Composable
fun BottomNavigationMenu(navController: NavHostController) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        IconButton(onClick = { navController.navigate("registroCliente") }) {
            Icon(Icons.Default.Add, contentDescription = "Registro")
        }
        IconButton(onClick = { navController.navigate("historial") }) {
            Icon(Icons.Default.History, contentDescription = "Historial")
        }
    }
}
