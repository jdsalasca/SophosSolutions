package com.example.sophossolutions.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.rounded.Description
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sophossolutions.navigation.Screen


@Composable
fun MenuElement(
    icon_card: ImageVector = Icons.Filled.Description,
    card_text: String = "Enviar Documentos",
    navController: NavController,
    route: String = Screen.Home.route,
    email: String? = "not"

    ) {

    Card(
        modifier = Modifier
            .padding(10.dp)
            .clickable {
                navController.navigate(
                    route = route

                )
            },
        shape = RoundedCornerShape(20.dp),
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {

                Icon(imageVector = icon_card, contentDescription = "location")


                Text(
                    style = MaterialTheme.typography.body1.copy(
                        color = Color.Black
                    ),
                    text = "  ${card_text}"
                )
            }
            Divider(color = Color.Gray, thickness = 2.dp)
            Row(
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                Button(


                    onClick = {
                        //TODO Send a document
                    }) {
                    Text("Ingresar")

                }

            }

        }


    }


}