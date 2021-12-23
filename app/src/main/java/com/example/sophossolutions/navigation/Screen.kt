package com.example.sophossolutions.navigation

sealed class Screen (val route:String) {
    object MainScreen: Screen ("main_screen")
    object Home: Screen ("HomeScreen")
    object MapScreen: Screen ("MapScreen")
    object CitySelector: Screen("CitySelector")
    object SendDocument: Screen("SendDocument")
    object Gallery: Screen ("PhotoFromGallery")
    object Permissions: Screen ("HandlePermissions")
    object SeeDocuments : Screen ("SeeDocuments")
    object DocumentView: Screen("DocumentView")

    fun withArgs (vararg args:String) : String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
