package com.example.sophossolutions.repository

import android.util.Log
import com.example.sophossolutions.datasource.IDocumentDAO
import com.example.sophossolutions.models.DocumentResponse
import com.example.sophossolutions.models.Documents
import com.example.sophossolutions.models.DocumentsItem
import com.example.sophossolutions.models.NewDocument
import com.example.sophossolutions.util.Resource
import retrofit2.http.Body
import retrofit2.http.Query
import javax.inject.Inject

interface DocumentRepository {
    suspend fun getAllDocuments(@Query("correo") correo: String = "jdsalasc@unal.edu.co"): Resource<Documents>
    suspend fun getAllDocumentsById(@Query("idRegistro") idRegistro: String): Resource<Documents>
    suspend fun newDocument(@Body document: DocumentsItem): Resource<DocumentResponse>


}

class DocumentRepositoryImp @Inject constructor(
    private val iDocumentDAO: IDocumentDAO
) : DocumentRepository {

    override suspend fun getAllDocuments(correo: String): Resource<Documents> {

        val documents: Documents
        if (iDocumentDAO.getAllDocuments(correo).isSuccessful) {
            documents = iDocumentDAO.getAllDocuments(correo).body()!!

            return Resource.Success(documents)


        } else {
            return Resource.Error("Documents don't found")
        }
    }


    override suspend fun getAllDocumentsById(idRegistro: String): Resource<Documents> {

        val documents: Documents
        if (iDocumentDAO.getAllDocuments(idRegistro).isSuccessful) {
            documents = iDocumentDAO.getAllDocumentsById(idRegistro).body()!!

            return Resource.Success(documents)


        } else {
            return Resource.Error("Document don't found")
        }

    }

    override suspend fun newDocument(document: DocumentsItem): Resource<DocumentResponse> {

        try {
            Log.d("Document email", document.Correo.toString())
            val result= document.Adjunto!!.replace("\n","")
            val newDocument = NewDocument(
                adjunto = result,
                apellido = document.Apellido!!,
                ciudad = document.Ciudad!!,
                correo = document.Correo!!,
                identificacion = document.Identificacion!!,
                nombre = document.Nombre!!,
                tipoAdjunto = document.TipoAdjunto!!,
                tipoId = document.TipoId!!
            )
            Log.d("Document Created", newDocument.toString())

            val response = iDocumentDAO.newDocument(document = newDocument).body()
            Log.d("Document Created", response.toString())
            return Resource.Success(data = response!!)

        } catch (e: Exception) {
            return Resource.Error("The Document doesn't was created")

        }
    }
}