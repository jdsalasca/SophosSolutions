package com.example.sophossolutions.presentation.senddocument


import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sophossolutions.presentation.components.HandlePermissions
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sophossolutions.R
import com.example.sophossolutions.models.DocumentsItem
import com.example.sophossolutions.presentation.components.Header

@ExperimentalPermissionsApi
@Composable
fun DocumentForm(
    email: String? = "",
    navController: NavController

) {
    val viewModel: SendDocumentViewModel = hiltViewModel()
    HandlePermissions()
    val documentToSend =
        remember { mutableStateOf(DocumentsItem("", "", "", "", "", "", "", "", "", "")) }

    val documentType = remember { mutableStateOf("") }

    val openDialog = remember { mutableStateOf(false) }


    //gallery
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }

    val attachedImage = remember { mutableStateOf("") }

    val launcherGallery = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) {

        if (Build.VERSION.SDK_INT < 28) {
            bitmap.value = MediaStore.Images
                .Media.getBitmap(context.contentResolver, it)

        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver, it!!)
            bitmap.value = ImageDecoder.decodeBitmap(source)
        }
    }

    val launcherCamera =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {

            bitmap.value = it!!
        }

//charging cities
    viewModel.getAllCities()
    val state = viewModel.cities.collectAsState().value


    var cities: MutableList<String> = mutableListOf()
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
    if (!state.cities.isNullOrEmpty()) {

        showLoading = false

        Scaffold(

            topBar = {
                Box {
                    Header(name = "Enviar Documento", navController = navController)
                }

            },
            floatingActionButton = {
                FloatingActionButton(
                    backgroundColor = Color.Red,
                    onClick = {}
                ) {
                    Icon(Icons.Filled.Send, "")
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.99f)
                        .clip(RoundedCornerShape(20.dp))
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(enabled = true, state = ScrollState(1)),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Envio de Documentación",
                            fontSize = 25.sp,
                            style = MaterialTheme.typography.h5.copy(
                                fontWeight = FontWeight.Light

                            )
                        )
                        Spacer(modifier = Modifier.padding(20.dp))
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                            val documentTypes =
                                listOf(
                                    "Seleccione un tipo de documento",
                                    "Cédula de Ciudadanía",
                                    "Tarjeta de Identidad",
                                    "Cedula de extranjería",
                                    " Pasaporte"
                                )

                            var isSelectDocumentTypeOpen by remember { mutableStateOf(false) }

                            var documentTypeSelecter by remember { mutableStateOf(0) }



                            ComposeMenu(

                                menuItems = documentTypes,
                                menuExpandedState = isSelectDocumentTypeOpen,
                                seletedIndex = documentTypeSelecter,
                                updateMenuExpandStatus = {
                                    isSelectDocumentTypeOpen = true
                                },
                                onDismissMenuView = {
                                    isSelectDocumentTypeOpen = false
                                },
                                onMenuItemclick = { index ->
                                    documentTypeSelecter = index
                                    isSelectDocumentTypeOpen = false
                                }
                            )


                            val idNumberValue = remember { mutableStateOf("") }

                            OutlinedTextField(
                                value = idNumberValue.value,
                                onValueChange = { idNumberValue.value = it },
                                label = {
                                    Row() {
                                        Icon(
                                            imageVector = Icons.Filled.Person,
                                            contentDescription = "tipo de documento"
                                        )
                                        Text(text = "Numero de documento")
                                    }
                                },
                                placeholder = { Text(text = "Numero de documento") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(0.8f)
                            )
                            val firstNameValue = remember { mutableStateOf("") }

                            OutlinedTextField(
                                value = firstNameValue.value,
                                onValueChange = { firstNameValue.value = it },
                                label = { Text(text = "Nombres") },
                                placeholder = { Text(text = "Nombres") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(0.8f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii)
                            )

                            val lastNameValue = remember { mutableStateOf("") }

                            OutlinedTextField(
                                value = lastNameValue.value,
                                onValueChange = { lastNameValue.value = it },
                                label = { Text(text = "Apellidos") },
                                placeholder = { Text(text = "Apellidos") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(0.8f)
                            )

                            var emailValue = remember { ("${email}") }
                            OutlinedTextField(
                                value = emailValue,
                                onValueChange = { emailValue = it },
                                label = { Text(text = "email") },
                                placeholder = { Text(text = "email") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(0.8f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                            )


                            var cities: MutableList<String> = mutableListOf()
                            cities.add("Ciudad")

                            for (item in state.cities!!) {
                                if (!cities.contains(item.toString())) {
                                    cities.add(item.toString())
                                }
                            }
                            var cityPickerExpanded by remember { mutableStateOf(false) }

                            var selectedIndexCity by remember { mutableStateOf(0) }



                            ComposeMenu(

                                menuItems = cities,
                                menuExpandedState = cityPickerExpanded,
                                seletedIndex = selectedIndexCity,
                                updateMenuExpandStatus = {
                                    cityPickerExpanded = true
                                },
                                onDismissMenuView = {
                                    cityPickerExpanded = false
                                },
                                onMenuItemclick = { index ->
                                    selectedIndexCity = index
                                    cityPickerExpanded = false
                                }
                            )

                            val attachmentType =
                                listOf(
                                    "Tipo de adjunto",
                                    "Certificado de cuenta",
                                    "Cédula",
                                    "Factura",
                                    "Incapacidad"
                                )

                            var attachmentTypeExpanded by remember { mutableStateOf(false) }

                            var selectedIndexattachmentType by remember { mutableStateOf(0) }



                            ComposeMenu(

                                menuItems = attachmentType,
                                menuExpandedState = attachmentTypeExpanded,
                                seletedIndex = selectedIndexattachmentType,
                                updateMenuExpandStatus = {
                                    attachmentTypeExpanded = true
                                },
                                onDismissMenuView = {
                                    attachmentTypeExpanded = false
                                },
                                onMenuItemclick = { index ->
                                    selectedIndexattachmentType = index
                                    attachmentTypeExpanded = false
                                }
                            )



                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center


                            ) {

                                Button(
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),

                                    onClick = {
                                        openDialog.value = true

                                    }) {
                                    Icon(
                                        imageVector = Icons.Filled.PhotoCamera,
                                        contentDescription = "attach a file"
                                    )

                                    Text(text = "Documento")
                                }

                                if (openDialog.value) {

                                    AlertDialog(
                                        onDismissRequest = {
                                            // Dismiss the dialog when the user clicks outside the dialog or on the back
                                            // button. If you want to disable that functionality, simply use an empty
                                            // onCloseRequest.
                                            openDialog.value = false
                                        },
                                        title = {
                                            Row(
                                                horizontalArrangement = Arrangement.Center,
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.fillMaxSize()
                                            ) {
                                                Text(
                                                    text = "Seleccione una fuente",
                                                    textAlign = TextAlign.Center,
                                                    style = MaterialTheme.typography.body1
                                                )
                                            }

                                        },
                                        text = {

                                            Column(
                                                modifier = Modifier.fillMaxSize()
                                            ) {
                                                Row(
                                                    horizontalArrangement = Arrangement.Center,
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier.fillMaxWidth()
                                                ) {
                                                    Button(onClick = {

                                                        launcherCamera.launch(
                                                        )
                                                    }) {
                                                        Icon(
                                                            Icons.Filled.Camera,
                                                            contentDescription = "Select from camera",
                                                            modifier = Modifier.size(
                                                                ButtonDefaults.IconSize
                                                            )
                                                        )
                                                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                                        Text("Camara")
                                                    }
                                                    Button(onClick = {

                                                        launcherGallery.launch("image/*")

                                                    }) {
                                                        Icon(
                                                            Icons.Filled.Collections,
                                                            contentDescription = "Select from gallery",
                                                            modifier = Modifier.size(
                                                                ButtonDefaults.IconSize
                                                            )
                                                        )
                                                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                                        Text("Galeria")

                                                    }
                                                }



                                                bitmap.let {
                                                    val data = it.value

                                                    if (data != null) {

                                                        var image = viewModel.getResizedBitmap(
                                                            data,
                                                            200
                                                        )

                                                        var base64 =
                                                            viewModel.getBase64ImageString(image)
                                                        attachedImage.value = base64!!
                                                        Log.d("base 64", base64.toString()!!)
                                                        Log.d(
                                                            "bitmap size",
                                                            image!!.byteCount.toString()
                                                        )



                                                        if (image.byteCount > 200000) {

                                                            Toast.makeText(

                                                                context,
                                                                "Showing toast....",
                                                                Toast.LENGTH_LONG
                                                            ).show()


                                                        } else {

                                                            Image(
                                                                bitmap = image.asImageBitmap(),
                                                                contentDescription = null,
                                                                modifier = Modifier.size(400.dp)
                                                            )
                                                        }


                                                    }
                                                }
                                            }


                                        },
                                        confirmButton = {

                                            OutlinedButton(

                                                onClick = {
                                                    openDialog.value = false
                                                }) {
                                                Text("aceptar")
                                            }


                                        },
                                        dismissButton = {
                                            OutlinedButton(

                                                onClick = {
                                                    openDialog.value = false
                                                }) {
                                                Text("cancelar")
                                            }
                                        }
                                    )

                                }

                            }


                            Spacer(modifier = Modifier.height(20.dp))


                            ExtendedFloatingActionButton(
                                onClick = {

                                    if ( checkfields(context,attachedImage.value,lastNameValue.value, firstNameValue.value,
                                            documentTypes[documentTypeSelecter],idNumberValue.value,  cities[selectedIndexCity],attachmentType[selectedIndexattachmentType],
                                            emailValue )){

                                        documentToSend.value.Adjunto = attachedImage.value
                                        documentToSend.value.Apellido = lastNameValue.value
                                        documentToSend.value.Nombre = firstNameValue.value
                                        documentToSend.value.TipoId = documentTypes[documentTypeSelecter]
                                        documentToSend.value.Identificacion = idNumberValue.value
                                        documentToSend.value.Ciudad = cities[selectedIndexCity]
                                        documentToSend.value.TipoAdjunto = attachmentType[selectedIndexattachmentType]
                                        documentToSend.value.Correo =  emailValue

                                        viewModel.newDocument(documentToSend.value)

                                        Log.d("Document to send", documentToSend.value.toString())

                                    }


                                },
                                backgroundColor = Color.Red,

                                text = { Text("Enviar") },
                                icon = {
                                    Icon(
                                        Icons.Filled.Send,
                                        contentDescription = "Favorite"
                                    )
                                },
                            )
                        }


                    }


                }

            }

        }






    }
}


@Composable
fun ComposeMenu(
    menuItems: List<String>,
    menuExpandedState: Boolean,
    seletedIndex: Int,
    updateMenuExpandStatus: () -> Unit,
    onDismissMenuView: () -> Unit,
    onMenuItemclick: (Int) -> Unit,
) {
    Box(

        modifier = Modifier
            .fillMaxHeight()

            .fillMaxWidth(0.8f)
            .clip(shape = RoundedCornerShape(30))
            .wrapContentSize(Alignment.TopStart)
            .padding(top = 10.dp)

            .clickable(
                onClick = {
                    updateMenuExpandStatus()
                },
            ),

        ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            val (lable, iconView) = createRefs()

            Text(
                text = menuItems[seletedIndex],

                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(lable) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(iconView.start)
                        width = Dimension.fillToConstraints
                    }
            )

            val displayIcon: Painter = painterResource(

                id = R.drawable.ic_baseline_arrow_drop_down_24

            )

            Icon(
                painter = displayIcon,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp, 20.dp)
                    .constrainAs(iconView) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                tint = MaterialTheme.colors.onSurface
            )

            DropdownMenu(
                expanded = menuExpandedState,
                onDismissRequest = { onDismissMenuView() },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface)
            ) {
                menuItems.forEachIndexed { index, title ->
                    DropdownMenuItem(
                        onClick = {
                            if (index != 0) {
                                onMenuItemclick(index)
                            }
                        }) {
                        Text(text = title)
                    }
                }
            }
        }
    }
}
fun checkfields (context: Context, attachment : String?, lastname : String?, firtName: String?, documentType: String?,
                 idnumber: String?, city: String?, attachmentType: String, emailValue : String): Boolean {

    if (attachment.isNullOrEmpty() || lastname.isNullOrEmpty() || firtName.isNullOrEmpty()
        || documentType == "Seleccione un tipo de documento" || idnumber.isNullOrEmpty() || city == "ciudad"||
        attachmentType == "Tipo de adjunto"|| emailValue.isNullOrEmpty()) {
        Toast.makeText(
            context,
            "Por favor Revisa todos los campos",
            Toast.LENGTH_SHORT
        ).show()
        return false

    }else {
        Toast.makeText(
            context,
            "Creando registro...",
            Toast.LENGTH_SHORT
        ).show()
        return true
    }


}





