package com.example.sophossolutions.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sophossolutions.R
import com.example.sophossolutions.presentation.components.MenuElement
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.sophossolutions.navigation.Screen
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    email: String? = "",
    clave: String? = "",
    navController: NavController

) {
    val viewModel: HomeScreenViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        viewModel.getUserInformation(idUsuario = email!!, clave = clave!!)
    }

    var showLoading by remember { mutableStateOf(true) }

    val state = viewModel.state.collectAsState().value

    if (showLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(

                modifier = Modifier
                    .size(100.dp),
                color = MaterialTheme.colors.onError,
                strokeWidth = 6.dp
            )
        }

    }

    if (!state.nombre.isNullOrEmpty()) {
        var context = LocalContext.current
        val activity = context as FragmentActivity
        showLoading = false
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                IconButton(
                    onClick = {
                        coroutineScope.launch { scaffoldState.drawerState.open() }
                    }
                ) {
                    Icon(Icons.Filled.Menu, contentDescription = "....")
                }
            },
            drawerContent = {

                BottonsMenu(navController, email)


            },
        ) {
            LazyColumn {


                stickyHeader {
                    if (state.nombre != null) {
                        Header(state.nombre)
                    }

                }
                items(1) {

                    MenuElement(
                        icon_card = Icons.Filled.Description,
                        card_text = "Enviar Documento",
                        navController = navController,
                        route = Screen.SendDocument.withArgs(email!!)
                    )
                    MenuElement(
                        icon_card = Icons.Filled.FindInPage,
                        card_text = "Ver Documentos",
                        navController = navController,
                        route = Screen.SeeDocuments.route

                    )
                    MenuElement(
                        icon_card = Icons.Filled.LocationOn,
                        card_text = "Ver Oficinas",
                        navController = navController,
                        route = Screen.CitySelector.route
                    )


                }


            }
        }


    }


}

@Composable
fun Header(
    name: String = ""
) {

    Box(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(16.dp)
            .background(color = Color.Transparent)
    ) {
        Card(

            modifier = Modifier
                .fillMaxWidth()
                .width(100.dp)
                .background(color = Color.Transparent),
            shape = RoundedCornerShape(15.dp),
            elevation = 5.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),


                    painter = painterResource(id = R.drawable.ic_home_couple),
                    contentDescription = "Login Image",
                    contentScale = ContentScale.Fit

                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black
                                ),
                                startY = 200f
                            )
                        )
                )
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        style = MaterialTheme.typography.h5.copy(
                            color = MaterialTheme.colors.onSurface
                        ),
                        text = "Bienvenido ${name} "
                    )
                }

            }


        }
    }


}

@Composable
fun BottonsMenu (navController: NavController, email: String?) {


    TextButton(
        onClick = {

            navController.navigate(
                route = Screen.SendDocument.withArgs(email!!)

            )


        }
    ) {
        Icon(
            imageVector = Icons.Filled.Send,
            contentDescription = "Enviar Documento",
            modifier = Modifier.padding(end = 8.dp),
            tint = Color(green = 0, red = 0, blue = 0)
        )
        Text(text = "Enviar Documento")
    }

    TextButton(
        onClick = {

            navController.navigate(
                route = Screen.SeeDocuments.route

            )


        }
    ) {
        Icon(
            imageVector = Icons.Filled.FindInPage,
            contentDescription = "Ver Documentos",
            modifier = Modifier.padding(end = 8.dp),
            tint = Color(green = 0, red = 0, blue = 0)
        )
        Text(text = "Ver Documentos")
    }

    TextButton(
        onClick = {
            navController.navigate(
                Screen.CitySelector.route

            )


        }
    ) {
        Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = "Ver Oficinas",
            modifier = Modifier.padding(end = 8.dp),
            tint = Color(green = 0, red = 0, blue = 0)
        )
        Text(text = "Ver Oficinas")
    }

    TextButton(
        onClick = {
            navController.navigate(
                Screen.MainScreen.route

            ) {
                popUpTo(navController.graph.findStartDestination().id) {
                    inclusive = true
                }
            }

        }
    ) {
        Icon(
            imageVector = Icons.Filled.Logout,
            contentDescription = "Cerrar sesión",
            modifier = Modifier.padding(end = 8.dp),
            tint = Color(0xFFCC3333)
        )
        Text(text = "Cerrar sesión")
    }
}