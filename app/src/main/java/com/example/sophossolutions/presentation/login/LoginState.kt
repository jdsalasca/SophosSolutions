package com.example.sophossolutions.presentation.login

import androidx.annotation.StringRes


data class LoginState(

    val email :String = "",
    val password: String = "",
    var succesLogin: Boolean = false,
    val displayProgressBar: Boolean = false,
    var errorMessage: String? = ""



)
