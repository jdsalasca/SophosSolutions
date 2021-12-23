package com.example.sophossolutions.presentation.seedocuments

import com.example.sophossolutions.models.DocumentsItem

data class SeeDocumentState(
    var Count: Int? = 0,
    var DocumentsItems: List<DocumentsItem>? = emptyList(),
    var ScannedCount: Int? = 0


)
