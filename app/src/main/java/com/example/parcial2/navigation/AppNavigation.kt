package com.example.parcial2.navigation

import RegisterServiceScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.parcial2.repository.CarRepository
import com.example.parcial2.repository.ServiceRepository
import com.example.parcial2.repository.UserRepository
import com.example.parcial2.screen.RegisterClientScreen
import androidx.navigation.NavType
import com.example.parcial2.repository.RegisterRepository
import com.example.parcial2.scaffold.EditScaffold
import com.example.parcial2.scaffold.HistoryScaffold
import com.example.parcial2.scaffold.RegisterScaffold
import com.example.parcial2.screen.EditCarScreen
import com.example.parcial2.screen.EditClientScreen
import com.example.parcial2.screen.EditServiceScreen
import com.example.parcial2.screen.HistoryInfoScreen
import com.example.parcial2.screen.HistoryScreen
import com.example.parcial2.screen.RegisterCarScreen
//import com.example.parcial2.screen.RegisterServiceScreen

@Composable
fun AppNavigation(
    UserRepository: UserRepository,
    CarRepository: CarRepository,
    ServiceRepository: ServiceRepository,
    RegisterRepository: RegisterRepository
) {
    val navController = rememberNavController()
    var selectedTabIndex by remember { mutableStateOf(0) }

    NavHost(navController = navController, startDestination = "registroCliente") {

        // Pantalla de registro de cliente
        composable("registroCliente") {
            RegisterScaffold(navController = navController) {
                RegisterClientScreen(
                    userRepository = UserRepository,
                    navController = navController
                )
            }
        }

        // Pantalla de registro de vehículo con clienteId
        composable(
            "registroVehiculo/{clienteId}",
            arguments = listOf(navArgument("clienteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getInt("clienteId") ?: return@composable
            RegisterScaffold(navController = navController) {
                RegisterCarScreen(
                    clienteId = clienteId,
                    carRepository = CarRepository,
                    navController = navController
                )
            }
        }

        // Pantalla de registro de servicio con vehiculoId
        composable(
            "registroServicio/{vehiculoId}",
            arguments = listOf(navArgument("vehiculoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val vehiculoId = backStackEntry.arguments?.getInt("vehiculoId") ?: return@composable
            RegisterScaffold(navController = navController) {
                RegisterServiceScreen(
                    vehiculoId = vehiculoId,
                    serviceRepository = ServiceRepository,
                    registerRepository = RegisterRepository,
                    navController = navController
                )
            }
        }

        // Pantalla de historial
        composable("historial") {
            HistoryScaffold(navController = navController) {
                HistoryScreen(
                    navController = navController,
                    userRepository = UserRepository,
                    carRepository = CarRepository,
                    serviceRepository = ServiceRepository
                )
            }
        }

        // Pantalla de detalles del historial
        composable(
            route = "historialInfo/{clienteId}",
            arguments = listOf(navArgument("clienteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getInt("clienteId") ?: return@composable
            HistoryScaffold(navController = navController) {
                HistoryInfoScreen(
                    clienteId = clienteId,
                    userRepository = UserRepository,
                    carRepository = CarRepository,
                    registerRepository = RegisterRepository,
                    serviceRepository = ServiceRepository,
                    navController = navController
                )
            }
        }

        // Pantalla de edición de cliente
        composable(
            route = "historialInfo/{clienteId}",
            arguments = listOf(navArgument("clienteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getInt("clienteId") ?: return@composable
            HistoryScaffold(navController = navController) {
                HistoryInfoScreen(
                    clienteId = clienteId,
                    userRepository = UserRepository,
                    carRepository = CarRepository,
                    registerRepository = RegisterRepository,
                    serviceRepository = ServiceRepository,
                    navController = navController
                )
            }
        }

        // Pantalla de edición que incluye el TabRow para cliente, vehículo y servicio
        composable(
            "editarTabs/{clienteId}/{vehiculoId}/{servicioId}/{registroId}",
            arguments = listOf(
                navArgument("clienteId") { type = NavType.IntType },
                navArgument("vehiculoId") { type = NavType.IntType },
                navArgument("servicioId") { type = NavType.IntType },
                navArgument("registroId") { type = NavType.IntType }  // Asegúrate de agregar el registroId
            )
        ) { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getInt("clienteId") ?: return@composable
            val vehiculoId = backStackEntry.arguments?.getInt("vehiculoId") ?: return@composable
            val servicioId = backStackEntry.arguments?.getInt("servicioId") ?: return@composable
            val registroId = backStackEntry.arguments?.getInt("registroId") ?: return@composable  // Obtener el registroId

            EditScaffold(
                navController = navController,
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { index -> selectedTabIndex = index },
                content = {
                    when (selectedTabIndex) {
                        0 -> EditClientScreen(clienteId = clienteId, userRepository = UserRepository, navController = navController)
                        1 -> EditCarScreen(vehiculoId = vehiculoId, carRepository = CarRepository, navController = navController)
                        2 -> EditServiceScreen(servicioId = servicioId, registroId = registroId, serviceRepository = ServiceRepository, registerRepository = RegisterRepository, navController = navController)
                    }
                }
            )
        }
    }
}
