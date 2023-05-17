package com.tusxapps.mealapp.ui.main.meal.create

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tusxapps.mealapp.ui.navigation.Graph
import java.io.File
import java.io.FileOutputStream

@Composable
fun CreateMealScreen(navController: NavController, viewModel: CreateMealViewModel, mealId: Int?) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state.errorMessage) {
        if (state.errorMessage != null)
            Toast.makeText(context, state.errorMessage, Toast.LENGTH_SHORT).show()
    }

    LaunchedEffect(Unit) {
        mealId?.let {
            viewModel.setMeal(it) // сделать чтобы еда не добавлялась а редачилась
        }
    }

    CreateMealScreen(
        state = state,
        onPriceChange = viewModel::onPriceChange,
        onDescChange = viewModel::onDescChange,
        onNameChange = viewModel::onNameChange,
        onAddClick = {
            viewModel.onAddClick {
                navController.navigate(Graph.Main.route) {
                    popUpTo(Graph.Root.route)
                }
            }
        },
        onPhotoAdd = viewModel::onPhotoAdd
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateMealScreen(
    state: CreateMealScreenState,
    onPriceChange: (String) -> Unit,
    onDescChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onAddClick: () -> Unit,
    onPhotoAdd: (Uri) -> Unit
) {
    val context = LocalContext.current
    val photoRequestLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            it?.let { uri ->
                context.contentResolver.openInputStream(uri).use {
                    val tempFile = File.createTempFile("photo", ".jpg", context.cacheDir)
                    val output = FileOutputStream(tempFile)
                    it?.copyTo(output)
                    onPhotoAdd(tempFile.toUri())
                }
            }
        }
    )

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = state.mealName,
            onValueChange = onNameChange,
            singleLine = true,
            label = { Text(text = "Название") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.meanDescription,
            onValueChange = onDescChange,
            label = { Text(text = "Описание") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
        )
        Button(
            onClick = {
                photoRequestLauncher.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Фото")
        }
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(state.photoUri)
                .build(),
            contentDescription = "",
            Modifier
                .weight(0.5f)
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = state.price,
            onValueChange = {
                val regex = Regex(
                    pattern = "([+-]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*))(?:[Ee]([+-]?\\d+))?",
                    options = setOf(RegexOption.IGNORE_CASE)
                )
                if (it.matches(regex))
                    onPriceChange(it)
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text(text = "Цена") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = onAddClick) {
            Text(text = "Добавить")
        }
    }
}