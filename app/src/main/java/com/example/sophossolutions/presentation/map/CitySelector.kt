package com.example.sophossolutions.presentation.map

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.example.sophossolutions.R
import com.example.sophossolutions.models.Office
import com.example.sophossolutions.presentation.components.CityPicker
import com.example.sophossolutions.presentation.components.Header
import com.example.sophossolutions.util.Resource



@Composable
fun CitySelector(
    navController: NavController


) {
    val viewModel: LocationViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        viewModel.getAllOffices()

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

    if (!state.Items.isNullOrEmpty()) {

        showLoading = false


        Scaffold(
            backgroundColor = Color.LightGray,
            topBar = {
                Box {
                    Header(name = "      Oficinas", navController = navController)
                }

            }
        ) {
            var cities: MutableList<String> = mutableListOf()
            for (item in state.Items!!) {
                if (!cities.contains(item.Ciudad.toString())) {
                    cities.add(item.Ciudad.toString())
                }
            }

            LazyColumn() {
                item { CityPicker("Current Location", resource = R.drawable.ic_baseline_location_searching_24, navController) }
                items((cities.size / 2)) { city ->


                    var numbers: List<Int> = listOf(0,2,4)

                    LazyRow() {
                        items(cities.subList(numbers[city],numbers[city]+1)){
                            CityPicker(cities.get(numbers[city]), resource = R.drawable.ic_baseline_location_city_24, navController)
                            CityPicker(cities.get(numbers[city]+1), resource = R.drawable.ic_baseline_location_city_24, navController)
                        }
                    }



                }


            }
        }

    }
}






