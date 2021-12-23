package com.example.sophossolutions.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.example.sophossolutions.navigation.Screen

@Composable
fun LoginDialog (
    showStatus: Boolean = false,
    errorMesage: String = ""
) {
    var showDialog by remember { mutableStateOf(showStatus) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            title = {
                Row() {
                    Text(text = "${errorMesage}")

                }
            },
            text = { },
            dismissButton = {

            },
            confirmButton = {

                OutlinedButton(onClick = {
                    showDialog = false

                }) {
                    Text("Ok")
                }


            }

        )
    }
}