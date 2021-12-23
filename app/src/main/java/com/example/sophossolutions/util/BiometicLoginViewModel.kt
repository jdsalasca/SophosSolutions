package com.example.sophossolutions.util

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.os.Build

import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import android.Manifest

import android.os.Bundle
import android.os.CancellationSignal
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import androidx.biometric.BiometricPrompt
import com.example.sophossolutions.R
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.example.sophossolutions.presentation.map.LocationsState
import com.example.sophossolutions.repository.SSUserRespositoryImp
import kotlinx.coroutines.flow.MutableStateFlow


class BiometicLoginViewModel constructor(
    context: Context,
    fragmentActivity: Activity


) : ViewModel(


) {

    val localcontext = context
    val activity = context as Activity
    var pm: PackageManager? = context.packageManager
    val fragmentActivity = fragmentActivity as FragmentActivity

    val state = MutableLiveData(false)

    fun showBiometricPrompt(correo: String? = "", clave: String? = "") {

        val correo = correo
        val clave = clave


        var promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("My App's Authentication")
            .setSubtitle("Please login to get access")
            .setDescription("My App is using Android biometric authentication")
            .setNegativeButtonText("Cancelar")
            .build()

        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(
                    localcontext,
                    "Authentication Error code: $errorCode",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(
                    localcontext,
                    "Authentication Fails for Unknow reason",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                setState(true)

                Toast.makeText(localcontext, "Authentication Succeeded", Toast.LENGTH_SHORT).show()

            }
        }

        val biometricPrompt = BiometricPrompt(fragmentActivity, callback)
        biometricPrompt.authenticate(promptInfo)



    }

    fun setState(value: Boolean? = true): Boolean {
        state.value = value
        return state.value!!


    }


}

data class BiometicState(
    var istrue: Boolean? = false

)






