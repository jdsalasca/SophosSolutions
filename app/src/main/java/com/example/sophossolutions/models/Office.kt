package com.example.sophossolutions.models

import androidx.compose.runtime.MutableState

data class Office(
    val Count: Int? = 0,
    val Items: List<Item>? = emptyList(),
    val ScannedCount: Int? = 0
)