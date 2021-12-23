package com.example.sophossolutions.datasource

import com.example.sophossolutions.models.SSUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IUserDAO {

    @GET("RS_Usuarios")
    suspend fun login (@Query("idUsuario") idUsuario: String, @Query("clave") clave: String): SSUser

}