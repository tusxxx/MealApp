package com.tusxapps.mealapp.ui.main.cart

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
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tusxapps.mealapp.R
import com.tusxapps.mealapp.domain.meal.Meal
import com.tusxapps.mealapp.ui.navigation.Screen

@Composable
fun CartScreen(navController: NavController, viewModel: CartViewModel) {
    val state by viewModel.state.collectAsState()
    CartScreen(
        state = state,
        onMinusItemClick = viewModel::onMinusItemClick,
        onPlusItemClick = viewModel::onPlusItemClick,
        onBuyClick = {},
        onMealClick = { navController.navigate(Screen.Meal.createRoute(it.name)) },
        onBuyAllClick = { navController.navigate(Screen.Order.route) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CartScreen(
    state: CartScreenState,
    onMinusItemClick: (Meal) -> Unit,
    onPlusItemClick: (Meal) -> Unit,
    onBuyClick: (Meal) -> Unit,
    onMealClick: (Meal) -> Unit,
    onBuyAllClick: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CartTopBar(state = state) }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            CartMealList(
                state = state,
                onMinusItemClick = onMinusItemClick,
                onPlusItemClick = onPlusItemClick,
                onBuyClick = onBuyClick,
                onMealClick = onMealClick
            )
            Button(
                onClick = onBuyAllClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 8.dp),
                enabled = state.meals.isNotEmpty()
            ) {
                Text(text = stringResource(R.string.to_buy))
            }
        }
    }
}

@Composable
private fun CartMealList(
    state: CartScreenState,
    onMinusItemClick: (Meal) -> Unit,
    onPlusItemClick: (Meal) -> Unit,
    onBuyClick: (Meal) -> Unit,
    onMealClick: (Meal) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(state.meals.toList()) { mealToCount ->
            MealItem(
                meal = mealToCount.first,
                count = mealToCount.second,
                onMinusItemClick = onMinusItemClick,
                onPlusItemClick = onPlusItemClick,
                onBuyClick = onBuyClick,
                onMealClick = onMealClick
            )
        }
        item {
            Spacer(Modifier.height(128.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MealItem(
    meal: Meal,
    count: Int,
    onMinusItemClick: (Meal) -> Unit,
    onPlusItemClick: (Meal) -> Unit,
    onBuyClick: (Meal) -> Unit,
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
                    .size(128.dp)
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
                Spacer(Modifier.height(12.dp))
                Text(
                    text = meal.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        }
        Divider(Modifier.fillMaxWidth())
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            MealCounter(
                onMinusItemClick = onMinusItemClick,
                meal = meal,
                count = count,
                onPlusItemClick = onPlusItemClick
            )
//            OutlinedButton(
//                onClick = { onBuyClick(meal) }
//            ) {
//                Text(text = stringResource(R.string.buy))
//            }
        }
    }
}

@Composable
private fun MealCounter(
    onMinusItemClick: (Meal) -> Unit,
    meal: Meal,
    count: Int,
    onPlusItemClick: (Meal) -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { onMinusItemClick(meal) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_remove), contentDescription = null
            )
        }
        Text(text = count.toString())
        IconButton(onClick = { onPlusItemClick(meal) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add), contentDescription = null
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CartTopBar(state: CartScreenState) {
    TopAppBar(
        title = {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "Корзина(${state.meals.size})",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color.White
                )
                Text(
                    text = "К оплате: ${state.totalPrice}",
                    color = Color.LightGray,
                    fontSize = 16.sp
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
    )
}