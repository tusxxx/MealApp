package com.tusxapps.mealapp.ui.main.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tusxapps.mealapp.R
import com.tusxapps.mealapp.domain.meal.Meal
import com.tusxapps.mealapp.ui.navigation.Screen

@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel) {
    val state by viewModel.state.collectAsState()
    ProfileScreen(
        state = state,
        onMealClick = {
            navController.navigate(Screen.Meal.createRoute(it.id))
        },
        onExitClick = viewModel::onExitClick,
        onAuthClick = { navController.navigate(Screen.Login.route) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileScreen(
    state: ProfileScreenState,
    onMealClick: (Meal) -> Unit,
    onExitClick: () -> Unit,
    onAuthClick: () -> Unit,
) {
    Scaffold(topBar = { AccountTopBar(state, onExitClick) }, modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when {
                state.user?.login == "" || state.user == null -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Войдите в аккаунт",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(16.dp))
                        Button(onClick = onAuthClick) {
                            Text(text = "Авторизоваться")
                        }
                    }
                }
                state.user?.isAdmin == false -> {
                    Text(
                        text = "Курьер привезёт",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                    LazyColumn(
                        Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.orders.firstOrNull()?.cart?.meals?.toList().orEmpty()) {
                            MealItem(
                                meal = it.first,
                                count = it.second,
                                onMealClick = onMealClick
                            )
                        }
                    }
                }
                else -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "Перейдите в аккаунт пользователя для заказа",
                            modifier = Modifier.align(Alignment.Center),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountTopBar(state: ProfileScreenState, onExitClick: () -> Unit) {
    TopAppBar(
        title = {
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp), modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.account),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(text = state.user?.login.orEmpty() + ": Администратор".takeIf { state.user?.isAdmin == true }
                    .orEmpty(), color = Color.LightGray, fontSize = 16.sp)
            }
        },
        actions = {
            IconButton(onClick = onExitClick) {
                Icon(
                    imageVector = Icons.Filled.ExitToApp,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MealItem(
    meal: Meal,
    count: Int,
    onMealClick: (Meal) -> Unit,
) {
    ElevatedCard(modifier = Modifier.fillMaxWidth(), onClick = { onMealClick(meal) }) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(meal.imageUrl).crossfade(true).build(),
                contentDescription = null,
                modifier = Modifier
                    .size(98.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop,
            )
            Spacer(Modifier.width(12.dp))
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = meal.price.toString() + " руб.",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = meal.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                Text(text = "В количестве $count")
            }
        }
    }
}