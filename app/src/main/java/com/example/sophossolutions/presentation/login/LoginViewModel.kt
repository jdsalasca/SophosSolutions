
package com.example.sophossolutions.presentation.login
/*
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sophossolutions.R
import com.example.sophossolutions.presentation.login.SSUserViewModel
import com.example.sophossolutions.repository.SSUserRespositoryImp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LoginViewModel: ViewModel(



) {



val state: MutableState<LoginState> =  mutableStateOf(LoginState())



fun login (email: String, password:String) {

    Log.d("UserCredentials", "${email}  ${password}")

    if (email.isBlank() || password.isBlank()){
        state.value.errorMessage = R.string.error_input_empty
    }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

        state.value.errorMessage= R.string.error_not_a_valid_email
    }else if (email != "user@gmail.com"|| password != "password"){
        state.value.errorMessage= R.string.error_invalid_credentials
    }else null
            ///Aaca va la logica de si el usuario entro bien o mal las credenciales


    viewModelScope.launch {
        state.value = state.value.copy(
            displayProgressBar = true
        )
        delay(2000)
        state.value = state.value.copy(
              email = email,
            password = password
        )
        state.value = state.value.copy(
            displayProgressBar = false
        )
        state.value.succesLogin = true
    }
}

    fun hideErrorDialog ( ) {
        state.value = state.value.copy( errorMessage = null)
    }

}*/
