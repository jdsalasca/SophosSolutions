package com.example.sophossolutions.presentation.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sophossolutions.models.Office
import com.example.sophossolutions.repository.OfficeRepository
import com.example.sophossolutions.util.Resource
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class LocationViewModel @Inject constructor(
    private val officeRepositoryImp: OfficeRepository
) : ViewModel() {

    var currentLocation:LatLng = LatLng(0.0,0.0)

    @SuppressLint("MissingPermission")
    @Composable
fun getLocation (context: Context) {

    var fusedLocationClient: FusedLocationProviderClient  = LocationServices.getFusedLocationProviderClient(context)
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner,
        effect =
        {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_START) {

                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    currentLocation = LatLng(location.latitude, location.longitude)
                }
                Log.d("CORDENADAS LOCALES",location.toString())


            }



}


    private val _state by lazy { MutableStateFlow<LocationsState>(LocationsState()) }
    val state: StateFlow<LocationsState>
        get() = _state

    fun getAllOffices() {
        viewModelScope.launch(Dispatchers.IO) {

            var office: Resource<Office> = officeRepositoryImp.getAllOffices()
            var locationState: LocationsState =
                LocationsState(office.data?.Count, office.data?.Items, office.data?.ScannedCount, currentLocation)
            Log.d("Que llega al viewModel", office.data.toString())

            _state.value = locationState

        }
    }
}



