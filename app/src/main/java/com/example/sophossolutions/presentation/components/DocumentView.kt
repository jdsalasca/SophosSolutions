package com.example.sophossolutions.presentation.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sophossolutions.R
import com.example.sophossolutions.models.DocumentsItem
import com.example.sophossolutions.presentation.seedocuments.SeeDocumentsViewModel


@Composable
fun DocumentView(

    idRegistro: String? = "xxx xxx xxx xxx",
    navController: NavController

) {


    val viewModel: SeeDocumentsViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.getDocumentById(idRegistro = idRegistro!!)
    }
    val documentViewModel = viewModel.document.collectAsState().value


    Scaffold(

        topBar = {
            Box {
                Header(name = "Detalles envio", navController = navController)
            }

        }
    ) {

        var showLoading by remember { mutableStateOf(true) }

        if (showLoading) {

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(

                    modifier = Modifier
                        .size(100.dp),
                    color = MaterialTheme.colors.onError,
                    strokeWidth = 6.dp
                )
            }

        }

        if (!documentViewModel.DocumentsItems.isNullOrEmpty()) {
            showLoading = false


            var document = documentViewModel.DocumentsItems!![0]!!
            //DECODING IMAGE
            val imageBytes = Base64.decode(document.Adjunto, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            var image: Bitmap = decodedImage





            Box(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .fillMaxSize(),

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
                        val (surface) = createRefs()

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(500.dp)
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
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Informacion de la solicitud",
                                    style = MaterialTheme.typography.h4.copy(
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                Text(
                                    text = "Nombre solicitante: ${document.Nombre}  ${document.Apellido}",
                                    style = MaterialTheme.typography.body1.copy(
                                        fontWeight = FontWeight.Bold
                                    )
                                )

                                Spacer(modifier = Modifier.height(20.dp))
                                Text(
                                    text = "Identificacion:${document.TipoId} ${document.Identificacion}",
                                    style = MaterialTheme.typography.body1.copy(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                Text(
                                    text = "Tipo de Adjunto: ${document.TipoAdjunto}",
                                    style = MaterialTheme.typography.body1.copy(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Spacer(modifier = Modifier.height(20.dp))

                                Image(
                                    bitmap = image.asImageBitmap(),
                                    contentDescription = null,
                                    modifier = Modifier.size(400.dp)
                                )

                            }

                        }


                    }
                }
            }
        }
    }
}