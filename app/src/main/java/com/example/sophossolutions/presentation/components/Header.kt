package com.example.sophossolutions.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sophossolutions.navigation.Screen


@Composable
fun Header(
    name: String? = "not defined",
    navController: NavController
) {
    var showDialog by remember { mutableStateOf(false) }
    TopAppBar(

        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                ClickableText(text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
                        append("Regresar              ")
                    }
                },
                    onClick = {

                        showDialog = true

                        //TODO "NAVIGATE TO Home SCREEN"
                    }
                )

                Text(text = "$name")



                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = {
                            showDialog = false
                        },
                        title = {
                            Row() {
                                Text(text = "¿Desea volver al menú anterior?")

                            }
                        },
                        text = { },
                        dismissButton = {

                            OutlinedButton(onClick = {
                                showDialog = false

                            }) {
                                Text("No")
                            }
                        },
                        confirmButton = {

                            OutlinedButton(onClick = {
                                navController.popBackStack()

                            }) {
                                Text("Si")
                            }


                        }

                    )
                }

            }
        },
        backgroundColor = Color.White
    )


}


