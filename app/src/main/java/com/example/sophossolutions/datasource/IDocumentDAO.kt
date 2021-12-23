package com.example.sophossolutions.datasource

import com.android.volley.toolbox.HttpResponse
import com.example.sophossolutions.models.DocumentResponse
import com.example.sophossolutions.models.Documents
import com.example.sophossolutions.models.DocumentsItem
import com.example.sophossolutions.models.NewDocument
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IDocumentDAO {


    @GET("RS_Documentos")
    suspend fun getAllDocuments (@Query("correo") correo: String = "jdsalasc@unal.edu.co"): Response<Documents>

    @GET("RS_Documentos")
    suspend fun getAllDocumentsById (@Query("idRegistro") idRegistro: String): Response<Documents>

    @POST("RS_Documentos")
    suspend fun newDocument (@Body document: NewDocument): Response<DocumentResponse>

}