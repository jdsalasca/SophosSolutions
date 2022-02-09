package com.example.sophossolutions.presentation.seedocuments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sophossolutions.models.DocumentsItem
import com.example.sophossolutions.navigation.Screen
import com.example.sophossolutions.presentation.components.Header


@Composable
fun SeeDocuments(
    navController: NavController
) {
    val viewModel: SeeDocumentsViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        viewModel.getDocuments()
    }
    val state = viewModel.state.collectAsState().value
    Scaffold(
        topBar = {
            Box {
                Header(name = "Ver documentos", navController = navController)
            }

        }
    ) {
        var showLoading by remember { mutableStateOf(true) }
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
        if (!state.DocumentsItems.isNullOrEmpty()) {

            showLoading = false

            LazyColumn {

                items(1) {
                    for (document in state.DocumentsItems!!) {
                        Document(document, navController)

                    }
                }

            }


        }


    }
}
@Composable
fun Document(
    document: DocumentsItem = DocumentsItem(
        "not defined",
        "not defined",
        "not defined",
        "not defined",
        "not defined",
        "not defined",
        "not defined",
        "not defined",
        "not defined",
        "not defined"

    ),
    navController: NavController

) {

    val date = document.Fecha!!.substring(startIndex = 0, endIndex = 10)
    val firstName = document.Nombre
    val lastName = document.Apellido
    val type = document.TipoAdjunto
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable {
                navController.navigate(
                    route = Screen.DocumentView.withArgs(document.IdRegistro.toString())


                )


            },
        elevation = 10.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            ConstraintLayout {
                val (icon, text) = createRefs()

                Column(


                    modifier = Modifier
                        .constrainAs(text) {

                        }
                        .fillMaxSize()
                        .padding(15.dp)
                ) {
                    Row {

                        Text(text = buildAnnotatedString {
                            append(date)
                            append(" - ")
                            append(type.toString())
                        })


                    }

                    Text(text = "$firstName $lastName")


                }

                Icon(
                    modifier = Modifier
                        .constrainAs(icon) {
                            absoluteRight.linkTo(parent.end)

                        }
                        .size(80.dp)
                        .padding(horizontal = 15.dp),
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "see document"
                )
            }
        }


    }


}