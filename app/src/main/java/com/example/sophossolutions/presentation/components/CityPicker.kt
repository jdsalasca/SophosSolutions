package com.example.sophossolutions.presentation.components

import android.location.Location
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sophossolutions.R
import com.example.sophossolutions.navigation.Screen
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


@Composable
fun CityPicker(

    cityName: String = "NOT_Defined",
    resource: Int = R.drawable.ic_home_couple,
    navController: NavController,
    fusedLocationClient: FusedLocationProviderClient? = LocationServices.getFusedLocationProviderClient(LocalContext.current)
) {

/*if()

    fusedLocationClient.lastLocation
        .addOnSuccessListener { location : Location? ->
            // Got last known location. In some rare situations this can be null.
        }*/

    var painter = painterResource(id = resource)



    Box(
        modifier = Modifier
            .width(200.dp)
            .padding(16.dp)
    ) {

        Card(

            modifier = Modifier
                .clickable {
                    if (cityName != "NOT_Defined") {
                        navController.navigate(
                            Screen.MapScreen.withArgs(cityName)

                        )
                    }
                }
                .fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            elevation = 5.dp
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(

                    modifier = Modifier
                        .clip(RoundedCornerShape(20))
                        .size(100.dp),
                    painter = painter,
                    contentDescription = "couple c:",
                    contentScale = ContentScale.Crop
                )


                Text(
                    textAlign = TextAlign.Center,
                    text = "${cityName}", style = MaterialTheme.typography.body1.copy(
                        fontSize = 16.sp
                    )
                )


            }


        }
    }


}
