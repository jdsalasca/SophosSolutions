package com.example.sophossolutions

import LoginJetpackComposeTheme
import Navigation
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import dagger.hilt.android.AndroidEntryPoint

//@ExperimentalAnimationApi
@ExperimentalPermissionsApi
@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginJetpackComposeTheme {
                Navigation()
            }
        }

    }
}
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            LoginJetpackComposeTheme {
//                val navController = rememberAnimatedNavController()
//                BoxWithConstraints {
//                    AnimatedNavHost(
//                        navController = navController,
//                        startDestination = Destinations.Home.route
//                    ) {
//                        addLogin(navController)
//                        addHome()
//
//                    }
//                }
//
//            }
//        }
//    }
//}
//
//@ExperimentalAnimationApi
//fun NavGraphBuilder.addLogin(
//    navController: NavHostController
//
//) {
//    composable(
//        route = Destinations.Login.route,
//        enterTransition = { _, _ ->
//            slideInHorizontally(
//                initialOffsetX = { 1000 },
//                animationSpec = tween(500)
//            )
//        },
//        exitTransition = {  _, _ ->
//            slideOutHorizontally(
//                targetOffsetX = { -1000 },
//                animationSpec = tween(500)
//            )
//
//        },
//        popEnterTransition = {  _, _ ->
//            slideInHorizontally(
//                initialOffsetX = { -1000 },
//                animationSpec = tween(500)
//            )
//
//        },
//        popExitTransition = {  _, _ ->
//            slideOutHorizontally(
//                targetOffsetX = { 1000 },
//                animationSpec = tween(500)
//            )
//
//        }
//
//        ) {
//
//        val viewModel:  LoginViewModel = hiltViewModel()
//        val email = viewModel.state.value.email
//        val password = viewModel.state.value.password
//
//        if (viewModel.state.value.succesLogin) {
//            LaunchedEffect(key1 = Unit) {
//                navController.navigate(
//                    Destinations.Home.route +  "/$email" + "/$password") {
//                    popUpTo(Destinations.Login.route) {
//                        inclusive =true
//                    }
//                }
//            }
//        }else {
//            LoginScreen(
//                state = viewModel.state.value,
//                onLogin = viewModel::login,
//                OnDissmisDialog = viewModel::hideErrorDialog
//
//            )
//        }
//
//
//    }
//}
//
//
//@ExperimentalAnimationApi
//fun NavGraphBuilder.addHome(
//
//) {
//    composable(
//        route = Destinations.Home.route + "/{email}" + "/{password}",
//        arguments = Destinations.Home.arguments
//
//    ) {
//        backStackEntry ->
//        val email = backStackEntry.arguments?.getString("email")?: ""
//        val password = backStackEntry.arguments?.getString("password")?: ""
//
//        HomeScreen(email, password)
//
//    }
//}