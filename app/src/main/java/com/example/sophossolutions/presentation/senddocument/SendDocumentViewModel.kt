package com.example.sophossolutions.presentation.senddocument

import android.R.attr
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import android.graphics.BitmapFactory

import android.graphics.Bitmap
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import android.R.attr.bitmap
import android.util.Base64
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.sophossolutions.models.Office
import com.example.sophossolutions.presentation.map.LocationsState
import com.example.sophossolutions.repository.OfficeRepository
import com.example.sophossolutions.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import android.R.attr.bitmap
import com.example.sophossolutions.models.DocumentsItem
import com.example.sophossolutions.repository.DocumentRepositoryImp


@HiltViewModel
class SendDocumentViewModel @Inject constructor(
    private val officeRepositoryImp: OfficeRepository,
    private val documentRepositoryImp: DocumentRepositoryImp
) : ViewModel() {

    val cities by lazy { MutableStateFlow<Cities>(Cities()) }

    fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap? {
        var width = image.width
        var height = image.height
        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }

    fun getAllCities() {
        viewModelScope.launch(Dispatchers.IO) {

            var office: Resource<Office> = officeRepositoryImp.getAllOffices()
            var citiesByApi: MutableList<String> = mutableListOf()
            for (i in office.data!!.Items!!){
                citiesByApi.add(i.Ciudad!!)
            }
            var citiesExport = Cities(citiesByApi)


            Log.d("Que llega al viewModel", office.data.toString())

            cities.value = citiesExport

        }
    }
    fun newDocument (document: DocumentsItem) {
        viewModelScope.launch ( Dispatchers.IO) {
            var response = documentRepositoryImp.newDocument(document = document)
            Log.d("Documento creado", response.toString())

        }
    }

    fun getBase64ImageString(photo: Bitmap?): String?{
        val byteArrayOutputStream = ByteArrayOutputStream()
        photo!!.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)

        val byteArray = byteArrayOutputStream.toByteArray()
        val encoded = Base64.encodeToString(byteArray, Base64.DEFAULT)
        return encoded

    }
}