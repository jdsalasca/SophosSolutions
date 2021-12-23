package com.example.sophossolutions.presentation.seedocuments

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sophossolutions.models.Documents
import com.example.sophossolutions.repository.DocumentRepositoryImp
import com.example.sophossolutions.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SeeDocumentsViewModel @Inject constructor(
    private val documentRepositoryImp: DocumentRepositoryImp
) : ViewModel(){

    val state by lazy { MutableStateFlow<SeeDocumentState>(SeeDocumentState()) }

    fun getDocuments (correo:String = "jdsalasc@unal.edu.co") {

        viewModelScope.launch (Dispatchers.IO){
            var documents: Resource<Documents> = documentRepositoryImp.getAllDocuments(correo)
            var response: SeeDocumentState = SeeDocumentState(documents.data?.Count,
                documents.data?.Items, documents.data?.ScannedCount
            )
            Log.d("Documentos", response.toString())

            state.value = response





        }

    }
    val document by lazy { MutableStateFlow<SeeDocumentState>(SeeDocumentState()) }

    fun getDocumentById (idRegistro: String) {

        viewModelScope.launch (Dispatchers.IO){
            var documents: Resource<Documents> = documentRepositoryImp.getAllDocumentsById(idRegistro)
            var response: SeeDocumentState = SeeDocumentState(documents.data?.Count,
                documents.data?.Items, documents.data?.ScannedCount
            )
            Log.d("Documentos id", response.toString())

            document.value = response





        }
    }




}