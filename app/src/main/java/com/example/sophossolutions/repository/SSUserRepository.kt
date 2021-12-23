package com.example.sophossolutions.repository

import android.util.Log
import com.example.sophossolutions.datasource.IUserDAO
import com.example.sophossolutions.models.SSUser
import com.example.sophossolutions.models.User
import com.example.sophossolutions.util.Resource
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject

interface SSUserRepository {
    suspend fun login(@Query("idUsuario") idUsuario: String, @Query("clave") clave : String): Resource<SSUser>

}

class SSUserRespositoryImp @Inject constructor(
    private val iUserDAO: IUserDAO
) : SSUserRepository {

    override suspend fun login(idUsuario: String, clave: String): Resource<SSUser> {
        var user: SSUser =  iUserDAO.login(idUsuario, clave)
        Log.d("User credential in Repository", user.toString())

        if (user.nombre != null) {
            return Resource.Success<SSUser>(user)
        }
        else{
            return Resource.Error("datos incorrectos")
        }



    }
}
