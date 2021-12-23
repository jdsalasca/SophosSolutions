package com.example.sophossolutions.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sophossolutions.models.SSUser
import com.example.sophossolutions.repository.SSUserRespositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val ssUserRespositoryImp: SSUserRespositoryImp
) : ViewModel() {

    val state by lazy {
        MutableStateFlow<SSUser>(
            SSUser(
                false,
                false,
                "",
                "",
                ""
            )
        )
    }

    fun getUserInformation(idUsuario: String, clave: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val user = ssUserRespositoryImp.login(
                idUsuario = idUsuario,
                clave = clave
            )

            if (user.data != null) {
                val userInformation = SSUser(user.data.acceso,
                user.data.admin,
                user.data.apellido,
                user.data.id,
                user.data.nombre)

                state.value = userInformation

            }


        }
    }


}