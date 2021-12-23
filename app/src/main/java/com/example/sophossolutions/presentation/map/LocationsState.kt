package com.example.sophossolutions.presentation.map

import com.example.sophossolutions.models.Item
import com.example.sophossolutions.models.Office
import com.google.android.libraries.maps.model.LatLng

data class LocationsState(
    var Count: Int? = 0,
    var Items: List<Item>? = emptyList(),
    var ScannedCount: Int? = 0,
    var location: LatLng? = LatLng(0.0,0.0)

)
