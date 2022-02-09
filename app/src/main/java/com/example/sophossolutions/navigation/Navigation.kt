import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sophossolutions.navigation.Screen
import com.example.sophossolutions.presentation.LoginScreen
import com.example.sophossolutions.presentation.seedocuments.SeeDocuments
import com.example.sophossolutions.presentation.components.DocumentView
import com.example.sophossolutions.presentation.components.HandlePermissions
import com.example.sophossolutions.presentation.home.HomeScreen
import com.example.sophossolutions.presentation.map.CitySelector
import com.example.sophossolutions.presentation.map.GoogleMaps
import com.example.sophossolutions.presentation.senddocument.DocumentForm
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@RequiresApi(Build.VERSION_CODES.P)
@ExperimentalPermissionsApi
@Composable
fun Navigation() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {

        composable(route = Screen.MainScreen.route) {
            LoginScreen(navController = navController)
        }

        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.Home.route + "/{name}" + "/{clave}",

            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                    defaultValue = "Juan"
                    nullable = true

                },
                navArgument("clave") {
                    type = NavType.StringType
                    defaultValue = "xxx"
                    nullable = true
                }

            )
        ) { entry ->
            HomeScreen(
                email = entry.arguments?.getString("name"),
                clave = entry.arguments?.getString("clave"),
                navController
            )


        }
        composable(
            route = Screen.MapScreen.route + "/{cityName}",
            arguments = listOf(
                navArgument("cityName") {
                    type = NavType.StringType
                    defaultValue = "Default"
                    nullable = true

                })
        ) { entry ->
            GoogleMaps(cityName = entry.arguments?.getString("cityName"), navController)
        }

        composable(route = Screen.CitySelector.route) {
            CitySelector(navController)
        }
        composable(
            route = Screen.SendDocument.route + "/{email}",
            arguments = listOf(
            navArgument("email") {
                type = NavType.StringType
                defaultValue = "xxx"
                nullable
            })
        ) {  entry ->
            DocumentForm(email = entry.arguments?.getString("email"), navController)
        }
        composable(route = Screen.SeeDocuments.route) {
            SeeDocuments(navController)
        }
        composable(route = Screen.Permissions.route) {
            HandlePermissions()
        }
        composable(route = Screen.DocumentView.route + "/{idRegistro}",
            arguments = listOf(
                navArgument(name = "idRegistro") {
                    type = NavType.StringType
                    defaultValue = "Default"
                    nullable = true
                }
            )) { entry ->
            DocumentView(idRegistro = entry.arguments?.getString("idRegistro"), navController)

        }
    }
}


/*package com.example.sophossolutions.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType

import androidx.navigation.navArgument

sealed class Destinations(
    val route: String,
    val arguments: List<NamedNavArgument>


) {
    object PracticeCamp: Destinations("home2", emptyList())
    object Login : Destinations("login", emptyList())
    object Home : Destinations("home", emptyList())
//        listOf(
//            navArgument("email") {
//                type = NavType.StringType
//            },
//            navArgument("password") { type = NavType.StringType }
//        ))

}*/
