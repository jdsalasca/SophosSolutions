package com.example.sophossolutions.models

data class Documents(
    val Count: Int? = 0,
    val Items: List<DocumentsItem>? = emptyList(),
    val ScannedCount: Int? = 0
)