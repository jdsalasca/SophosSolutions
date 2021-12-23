package com.example.sophossolutions.presentation.login

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.util.Log
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sophossolutions.models.SSUser
import com.example.sophossolutions.repository.SSUserRespositoryImp
import com.example.sophossolutions.util.BiometicLoginViewModel
import com.example.sophossolutions.util.BiometicState
import com.example.sophossolutions.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log
import kotlin.properties.Delegates
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import kotlinx.coroutines.runBlocking


@HiltViewModel
class SSUserViewModel @Inject constructor(
    private val ssUserRespositoryImp: SSUserRespositoryImp,

    ) : ViewModel() {

    val state by lazy { MutableStateFlow<LoginState>(LoginState()) }

    var displayProgressBar = false
    var email = ""
    var successLogin = false
    var errorMessage = ""

    lateinit var localcontext: Context

    fun setContext(context: Context) {
        localcontext = context
    }

    var biometricLogin = false

    fun login(idUsuario: String, clave: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user: Resource<SSUser> = ssUserRespositoryImp.login(idUsuario, clave)

            if (user.message.isNullOrBlank()) {
                email = idUsuario
                successLogin = true
                biometricLogin = true
            }
            val newState: LoginState = LoginState(
                errorMessage = errorMessage,
                succesLogin = successLogin,
                password = "",
                displayProgressBar = false,
                email = email
            )
            state.value = newState
        }
    }

    var credenciales = MutableLiveData(mutableListOf(""))
    fun biometricLogin(idUsuario: String, clave: String): MutableList<String>? {
        val activity = localcontext as FragmentActivity
        var istrue = false
        val biometicLogin = BiometicLoginViewModel(
            context = localcontext,
            fragmentActivity = activity
        )
        biometicLogin.showBiometricPrompt()

        //TODO () Finish the logic to save creentials and login with Biometrics
        biometicLogin.state.observe(activity, Observer { it ->
            istrue = it
            val settings = PreferenceManager.getDefaultSharedPreferences(activity)
            val editor = settings.edit()
            val existidUsuario: String? = settings.getString("idclave", "")
            val existClave: String? = settings.getString("clave", "")
            if (istrue) {

                if (existidUsuario.isNullOrBlank() || existClave.isNullOrBlank()) {
                    if (checkFields(context = activity, idUsuario, clave)) {
                        Toast.makeText(
                            activity,
                            "Guardando Credenciales",
                            Toast.LENGTH_LONG
                        ).show()
                        editor.putString("idUsuario", idUsuario);
                        editor.putString("clave", clave);
                        editor.commit();
                        credenciales.value?.add(idUsuario)
                        credenciales.value?.add(clave)
                    }
                } else {
                    val savedIdUsuario = existidUsuario
                    val savedClave = existClave

                    credenciales.value?.add(savedIdUsuario)
                    credenciales.value?.add(savedClave)


                }
            }
        })

        return credenciales.value
    }


    fun succesLogin() {
        state.value = LoginState(
            errorMessage = errorMessage,
            succesLogin = false,
            password = "",
            displayProgressBar = false,
            email = email

        )
    }

    fun resetState() {
        viewModelScope.launch {
            delay(2000)
            val newState: LoginState = LoginState(
                errorMessage = "",
                succesLogin = successLogin,
                password = "",
                displayProgressBar = false,
                email = email
            )
            state.value = newState
        }

    }


    fun checkFields(context: Context, idUsuario: String?, clave: String?): Boolean {

        if (idUsuario == null || clave == null) {
            Toast.makeText(
                context,
                "Los campos no pueden estar vacios",
                Toast.LENGTH_LONG
            ).show()
            return false

        } else if (idUsuario.isBlank() || clave.isBlank()) {
            Toast.makeText(
                context,
                "Los campos no pueden estar vacios",
                Toast.LENGTH_LONG
            ).show()
            return false

        } else if (!Patterns.EMAIL_ADDRESS.matcher(idUsuario).matches()) {

            Toast.makeText(
                context,
                "Por favor ingrese un Email Correcto",
                Toast.LENGTH_LONG
            ).show()
            return false
        } else {
            return true
        }

    }
}

