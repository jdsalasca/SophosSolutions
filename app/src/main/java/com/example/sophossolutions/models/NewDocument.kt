package com.example.sophossolutions.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewDocument(

    @Json(name = "Adjunto")
    val adjunto: String,
    @Json(name = "Apellido")
    val apellido: String,
    @Json(name = "Ciudad")
    val ciudad: String,
    @Json(name = "Correo")
    val correo: String,
    @Json(name = "Identificacion")
    val identificacion: String,
    @Json(name = "Nombre")
    val nombre: String,
    @Json(name = "TipoAdjunto")
    val tipoAdjunto: String,
    @Json(name = "TipoId")
    val tipoId: String
)
