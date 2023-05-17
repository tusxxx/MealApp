//package com.tusxapps.mealapp.ui.main.meal.edit
//
//import android.net.Uri
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.PickVisualMediaRequest
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.gestures.ModifierLocalScrollableContainerProvider.value
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.Button
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.navigation.NavController
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import androidx.core.net.toUri
//import coil.compose.AsyncImage
//import coil.request.ImageRequest
//import java.io.File
//import java.io.FileOutputStream
//
//@Composable
//fun EditMealScreen(navController: NavController, viewModel: EditMealViewModel, mealId: Int) {
//    val state by viewModel.state.collectAsState()
//
//    LaunchedEffect(Unit) {
//        viewModel.setMeal(mealId)
//    }
//    EditMealScreen(state)
//}
//
//@Composable
//private fun EditMealScreen(state: EditMealScreenState) {
//    val context = LocalContext.current
//    var photoUri: Uri? by remember {
//        mutableStateOf(null)
//    }
//    val photoRequestLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.PickVisualMedia(),
//        onResult = {
//            it?.let { uri ->
//                context.contentResolver.openInputStream(uri).use {
//                    val tempFile = File.createTempFile("photo", ".jpg", context.cacheDir)
//                    val output = FileOutputStream(tempFile)
//                    it?.copyTo(output)
//                    onPhotoAdd(tempFile.toUri())
//                    photoUri = tempFile.toUri()
//                }
//            }
//        }
//    )
//
//    Column(
//        Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        state.meal?.let {
//            OutlinedTextField(
//                value = it.name,
//                onValueChange = onNameChange,
//                singleLine = true,
//                label = { Text(text = "Название") },
//                modifier = Modifier.fillMaxWidth()
//            )
//            OutlinedTextField(
//                value = it.description,
//                onValueChange = onDescChange,
//                label = { Text(text = "Описание") },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(0.5f)
//            )
//            Button(
//                onClick = {
//                    photoRequestLauncher.launch(
//                        PickVisualMediaRequest(
//                            ActivityResultContracts.PickVisualMedia.ImageOnly
//                        )
//                    )
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(text = "Фото")
//            }
//            AsyncImage(
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data(photoUri)
//                    .build(),
//                contentDescription = "",
//                Modifier
//                    .weight(0.5f)
//                    .fillMaxWidth()
//            )
//
//            OutlinedTextField(
//                value = it.price,
//                onValueChange = {
//                    val regex = Regex(
//                        pattern = "([+-]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*))(?:[Ee]([+-]?\\d+))?",
//                        options = setOf(RegexOption.IGNORE_CASE)
//                    )
//                    if (it.toString().matches(regex))
//                        onPriceChange(it)
//                },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
//                label = { Text(text = "Цена") },
//                modifier = Modifier.fillMaxWidth()
//            )
//            Button(onClick = onAddClick) {
//                Text(text = "Сохранить")
//            }
//        }
//    }
//}