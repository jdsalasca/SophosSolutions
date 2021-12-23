package com.example.sophossolutions.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.FindInPage
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sophossolutions.R
import com.example.sophossolutions.presentation.components.MenuElement
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sophossolutions.navigation.Screen


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    email: String? = "jdsalasc@unal.edu.co",
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

        showLoading = false


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
                        text = "Bienvenido ${name} :3 "
                    )
                }

            }


        }
    }


}