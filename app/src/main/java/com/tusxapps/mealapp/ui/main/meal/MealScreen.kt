package com.tusxapps.mealapp.ui.main.meal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun MealScreen(navController: NavController, viewModel: MealViewModel, mealName: String) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(mealName) {
        viewModel.setMeal(mealName)
    }
    MealScreen(state)
}

@Composable
private fun MealScreen(state: MealScreenState) {
    Box(modifier = Modifier) {
        AsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .align(Alignment.TopCenter),
            contentScale = ContentScale.Crop
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .align(Alignment.BottomCenter)
                .background(MaterialTheme.colorScheme.surface)
        ) {

        }
    }
}