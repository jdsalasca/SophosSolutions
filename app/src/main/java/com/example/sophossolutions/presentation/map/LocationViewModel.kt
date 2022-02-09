package com.example.sophossolutions.presentation.map

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
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

    private var currentLocation: LatLng = LatLng(0.0, 0.0)

    @SuppressLint("MissingPermission")
    @Composable
    fun GetLocation(context: Context) {

        val fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)
        val lifecycleOwner = LocalLifecycleOwner.current
        DisposableEffect(key1 = lifecycleOwner,
            effect =
            {
                val observer = LifecycleEventObserver { _, event ->
//                    if (event == Lifecycle.Event.ON_START) {
//
//                    }
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
                Log.d("CORDENADAS LOCALES", location.toString())


            }


    }


    private val _state by lazy { MutableStateFlow(LocationsState()) }
    val state: StateFlow<LocationsState>
        get() = _state

    fun getAllOffices() {
        viewModelScope.launch(Dispatchers.IO) {

            val office: Resource<Office> = officeRepositoryImp.getAllOffices()
            val locationState =
                LocationsState(
                    office.data?.Count,
                    office.data?.Items,
                    office.data?.ScannedCount,
                    currentLocation
                )

            _state.value = locationState

        }
    }
}



