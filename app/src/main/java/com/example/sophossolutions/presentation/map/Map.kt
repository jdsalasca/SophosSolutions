package com.example.sophossolutions.presentation.map

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.example.sophossolutions.presentation.components.HandlePermissions
import com.example.sophossolutions.presentation.components.Header
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.BitmapDescriptorFactory
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.MarkerOptions

import kotlinx.coroutines.*


@ExperimentalPermissionsApi
@Composable
fun GoogleMaps(
    cityName: String? = "Default",
    navController: NavController

) {

    Log.d("Name of city", cityName!!)


    val mapView = rememberMapViewWithLifeCycle()
    val viewModel: LocationViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.getAllOffices()

    }
    viewModel.GetLocation(LocalContext.current)
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(key1 = state.Count) {

    }

    if (!state.Items.isNullOrEmpty()) {

        HandlePermissions()


        Scaffold(

            topBar = {
                Box {
                    Header(name = "      Oficinas", navController = navController)
                }

            }

        ) {


            Box{

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    AndroidView(
                        { mapView }
                    ) { mapView ->
                        CoroutineScope(Dispatchers.Main).launch {

                            mapView.getMapAsync {


                                for (item in state.Items!!) {

                                    Log.d("Cordinadas", cityName.toString())



                                    if (cityName == item.Ciudad || cityName.toString() == "Ubicación Actual") {
                                        if (cityName.toString() == "Ubicación Actual") {
                                            it.moveCamera(
                                                CameraUpdateFactory.newLatLngZoom(
                                                    state.location,
                                                    it.minZoomLevel


                                                )
                                            )
                                            it.addMarker(
                                                MarkerOptions().position(
                                                    LatLng(
                                                        state.location?.latitude!!.toDouble(),
                                                        state.location?.longitude!!.toDouble()
                                                    )
                                                )
                                                    .title("Ubicacion Actual")
                                                    .icon(
                                                        BitmapDescriptorFactory.defaultMarker(
                                                            BitmapDescriptorFactory.HUE_BLUE
                                                        )
                                                    )
                                            )



                                        } else {
                                            it.moveCamera(
                                                CameraUpdateFactory.newLatLngZoom(
                                                    LatLng(
                                                        item.Latitud!!.toDouble(),
                                                        item.Longitud!!.toDouble()
                                                    ), it.minZoomLevel
                                                )
                                            )
                                        }

                                        it.addMarker(
                                            MarkerOptions().position(
                                                LatLng(
                                                    item.Latitud!!.toDouble(),
                                                    item.Longitud!!.toDouble()
                                                )
                                            )
                                                .title(item.Nombre)
                                                .icon(
                                                    BitmapDescriptorFactory.defaultMarker(
                                                        BitmapDescriptorFactory.HUE_GREEN
                                                    )
                                                )
                                        )
                                    }


                                }



                                it.mapType = 1
                                it.uiSettings.isZoomControlsEnabled = true

                            }

                        }
                    }
                }
            }

        }

    }


}


@Composable
fun rememberMapViewWithLifeCycle(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            id = com.google.maps.android.ktx.R.id.map_frame
        }
    }
    val lifeCycleObserver = rememberMapLifecycleObserver(mapView)
    val lifeCycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifeCycle) {
        lifeCycle.addObserver(lifeCycleObserver)
        onDispose {
            lifeCycle.removeObserver(lifeCycleObserver)
        }
    }

    return mapView
}

@Composable
fun rememberMapLifecycleObserver(mapView: MapView): LifecycleEventObserver =
    remember(mapView) {
        LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> throw IllegalStateException()
            }
        }
    }
