package com.example.sophossolutions.repository

import android.util.Log
import com.example.sophossolutions.datasource.IOfficeDAO
import com.example.sophossolutions.models.Office
import com.example.sophossolutions.util.Resource
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject

interface OfficeRepository {
    suspend fun getAllOffices(): Resource<Office>
    suspend fun getOfficesByCity(@Query("ciudad") ciudad: String): Resource<Office>
}


class OfficeRepositoryImp @Inject constructor(
    private val iOfficeDAO: IOfficeDAO
) : OfficeRepository {

    override suspend fun getAllOffices(): Resource<Office> {
        var offices: Office;
        if (iOfficeDAO.getAllOffices().isSuccessful) {

            offices = iOfficeDAO.getAllOffices().body()!!
            Log.d("Office Repository", offices.toString())

            if (offices.Items!!.isEmpty()) {
                return Resource.Error("offices not found")
            } else {
                return Resource.Success(offices)

            }
        }else {
            return Resource.Error("offices not found")
        }

    }

    override suspend fun getOfficesByCity(ciudad: String): Resource<Office> {
        var ciudades: Office = iOfficeDAO.getOfficesByCity(ciudad)
        if (ciudades.Items?.isEmpty() == true) {
            return Resource.Error("Offices not found")
        } else {
            return Resource.Success(ciudades)
        }
    }
}