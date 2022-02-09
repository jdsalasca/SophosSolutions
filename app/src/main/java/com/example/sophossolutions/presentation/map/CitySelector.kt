package com.example.sophossolutions.presentation.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sophossolutions.R
import com.example.sophossolutions.presentation.components.CityPicker
import com.example.sophossolutions.presentation.components.Header



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
    
    Surface (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        color =  Color.White
            ) {


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

                val cities: MutableList<String> = mutableListOf()
                for (item in state.Items!!) {
                    if (!cities.contains(item.Ciudad.toString())) {
                        cities.add(item.Ciudad.toString())
                    }
                }
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)) {
                    item { CityPicker("Ubicación Actual", resource = R.drawable.ic_baseline_location_searching_24, navController) }
                    items((cities.size / 2)) { city ->

                        val numbers: List<Int> = listOf(0,2,4)

                        LazyRow{
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

}






