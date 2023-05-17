package com.tusxapps.mealapp.ui.main.meal

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tusxapps.mealapp.R
import com.tusxapps.mealapp.ui.navigation.Graph
import com.tusxapps.mealapp.ui.navigation.Screen

@Composable
fun MealScreen(navController: NavController, viewModel: MealViewModel, mealId: Int) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val toastText = stringResource(R.string.added_to_cart)

    LaunchedEffect(mealId) {
        viewModel.setMeal(mealId)
    }
    MealScreen(state = state, onAddToCartClick = {
        viewModel.onAddToCartClick()
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
        navController.popBackStack()
    }, onEditClick = {
        state.meal?.id?.let {
            navController.navigate(Screen.CreateMeal.createRoute(it))
        }
    }, onDeleteMeal = {
        viewModel.onDeleteMeal(onSuccess = {
            navController.navigate(Graph.Main.route)
        })
    })
}

@Composable
private fun MealScreen(
    state: MealScreenState,
    onAddToCartClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteMeal: () -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(state.meal?.imageUrl)
                .crossfade(true).build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .align(Alignment.TopCenter),
            contentScale = ContentScale.Crop
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.65f)
                .align(Alignment.BottomCenter)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = state.meal?.name.orEmpty(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = (state.meal?.price?.toString() ?: "") + " руб.",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(text = state.meal?.description.orEmpty())
                Spacer(Modifier.height(48.dp))
            }
        }
        if (state.user?.isAdmin == false) {
            Button(
                onClick = onAddToCartClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(32.dp)
            ) {
                Text(text = stringResource(R.string.add_to_cart))
            }
        }
        if (state.user?.isAdmin == true) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(32.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onEditClick, modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Редактировать")
                }
                Button(
                    onClick = onDeleteMeal, modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Удалить")
                }
            }
        }
    }
}