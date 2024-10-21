package com.example.parcial2.scaffold

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScaffold(
    navController: NavHostController,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    content: @Composable () -> Unit
) {
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
                        Text("Edición")
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
                // TabRow para las pestañas de Cliente, Vehículo y Servicio
                TabRow(selectedTabIndex = selectedTabIndex) {
                    Tab(
                        selected = selectedTabIndex == 0,
                        onClick = { onTabSelected(0) },
                        text = { Text("Cliente") }
                    )
                    Tab(
                        selected = selectedTabIndex == 1,
                        onClick = { onTabSelected(1) },
                        text = { Text("Vehículo") }
                    )
                    Tab(
                        selected = selectedTabIndex == 2,
                        onClick = { onTabSelected(2) },
                        text = { Text("Servicio") }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Aquí se muestra el contenido de la pestaña actual
                content()
            }
        }
    )
}