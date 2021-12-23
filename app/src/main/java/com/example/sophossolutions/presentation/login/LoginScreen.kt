package com.example.sophossolutions.presentation

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.sophossolutions.R
import com.example.sophossolutions.presentation.login.SSUserViewModel
import com.example.sophossolutions.navigation.Screen
import com.example.sophossolutions.presentation.components.HandlePermissions
import com.example.sophossolutions.presentation.components.LoginDialog
import com.example.sophossolutions.presentation.components.RoundedButton
import com.example.sophossolutions.presentation.components.TransaprentTextField
import com.example.sophossolutions.util.BiometicLoginViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi


@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LoginScreen(navController: NavController) {

    val viewModel: SSUserViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState().value


    val emailValue = rememberSaveable { mutableStateOf("") }
    val passwordValue = rememberSaveable { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    viewModel.resetState()
    HandlePermissions()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Login Image",
            contentScale = ContentScale.Inside

        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            ConstraintLayout() {
                val (surface, fab) = createRefs()

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .constrainAs(surface) {
                            bottom.linkTo(parent.bottom)
                        },
                    color = Color.White,
                    shape = RoundedCornerShape(
                        topStartPercent = 8,
                        topEndPercent = 8
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceEvenly

                    ) {
                        Text(
                            text = "Bienvenido",
                            style = MaterialTheme.typography.h4.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Text(
                            text = "Ingresa a tu cuenta",
                            style = MaterialTheme.typography.h5.copy(
                                color = MaterialTheme.colors.primary

                            )
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            TransaprentTextField(
                                textFieldValue = emailValue,
                                textlabel = "Email",
                                keyboardType = KeyboardType.Email,
                                keyboardActions = KeyboardActions(
                                    onNext = {
                                        focusManager.moveFocus(FocusDirection.Down)
                                    }
                                ),
                                imeAction = ImeAction.Next
                            )

                            TransaprentTextField(
                                textFieldValue = passwordValue,
                                textlabel = "Password",
                                keyboardType = KeyboardType.Password,
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        focusManager.clearFocus()

                                        viewModel.login(emailValue.value, passwordValue.value)
                                    }
                                ),
                                imeAction = ImeAction.Done,
                                trailingIcon = {
                                    IconButton(onClick = {
                                        passwordVisibility = !passwordVisibility
                                    }
                                    ) {
                                        Icon(
                                            imageVector = if (passwordVisibility) {
                                                Icons.Default.Visibility

                                            } else {
                                                Icons.Default.VisibilityOff
                                            },
                                            contentDescription = "Toggle Password Icon"
                                        )

                                    }
                                },
                                visualTransformation = if (passwordVisibility) {
                                    VisualTransformation.None
                                } else {
                                    PasswordVisualTransformation()
                                }
                            )

                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Forgot Password",
                                style = MaterialTheme.typography.body1,
                                textAlign = TextAlign.End

                            )


                        }
                        var context = LocalContext.current
                        val activity = context as FragmentActivity

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)

                        ) {
                            RoundedButton(text = "Ingresa",
                                displayProgressBar = state.displayProgressBar,
                                onClick = {

                                    if (checkFields(context, emailValue.value, passwordValue.value))
                                        viewModel.login(emailValue.value, passwordValue.value)

                                    if (state.succesLogin) {
                                        viewModel.succesLogin()
                                        navController.navigate(
                                            Screen.Home.withArgs(
                                                emailValue.value,
                                                passwordValue.value
                                            )

                                        ) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                inclusive = true
                                            }
                                        }
                                        Log.d(
                                            "current destination",
                                            navController.currentDestination.toString()
                                        )

                                    }

                                }
                            )
                            var credenciales = viewModel.credenciales

                            viewModel.setContext(context = context)
                            Button(
                                onClick = {
                                    if (Build.VERSION.SDK_INT > 22)

                                        viewModel.biometricLogin(
                                            emailValue.value,
                                            passwordValue.value
                                        )

                                    Log.d ("CREDENCIALES", credenciales.toString())

                                },
                                modifier = Modifier
                                    .width(280.dp)
                                    .height(50.dp),
                                shape = RoundedCornerShape(50),
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Fingerprint,
                                    contentDescription = ""
                                )
                                Text(
                                    text = "Ingreso con Huella",
                                    style = MaterialTheme.typography.h6.copy(
                                        color = Color.White
                                    )
                                )
                            }


                            ClickableText(text = buildAnnotatedString {
                                append("Do not have an Account?")
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colors.primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append("Sign Up")
                                }
                            },
                                onClick = {//TODO "NAVIGATE TO REGISTER SCREEN"
                                }
                            )


                        }

                    }


                }
                FloatingActionButton(
                    modifier = Modifier
                        .size(72.dp)
                        .constrainAs(fab) {
                            top.linkTo(surface.top, margin = (.36).dp)
                            end.linkTo(surface.end, margin = 36.dp)

                        },
                    backgroundColor = MaterialTheme.colors.primary,
                    onClick = {}
                )
                {
                    Icon(
                        modifier = Modifier.size(42.dp),
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Forward Icon",
                        tint = Color.White
                    )

                }

            }
        }




        if (state.errorMessage != "") {

            Toast.makeText(
                LocalContext.current,
                "${state.errorMessage}",
                Toast.LENGTH_LONG
            ).show()
            viewModel.resetState()

        }

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
            Toast.LENGTH_SHORT
        ).show()
        return false

    } else {
        return true
    }

}
