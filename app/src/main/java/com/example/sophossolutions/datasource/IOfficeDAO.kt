package com.example.sophossolutions.datasource

import com.example.sophossolutions.models.Office
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IOfficeDAO {

    @GET("RS_Oficinas")
    suspend fun getAllOffices (): Response<Office>

    @GET("RS_Oficinas")
    suspend fun getOfficesByCity(@Query("ciudad")ciudad:String): Office

}