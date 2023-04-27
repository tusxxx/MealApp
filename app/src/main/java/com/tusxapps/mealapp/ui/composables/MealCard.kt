package com.tusxapps.mealapp.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tusxapps.mealapp.domain.meal.Meal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealCard(meal: Meal, onMealClick: (Meal) -> Unit) {
    val colors = listOf(
        Color.Transparent,
        Color.Transparent,
        Color.Transparent,
        MaterialTheme.colorScheme.surface
    )
    Card(
        onClick = { onMealClick(meal) },
        modifier = Modifier.size(width = 180.dp, height = 160.dp)
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(meal.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .drawBehind {
                        drawRect(Brush.verticalGradient(colors))
                    }
            ) {
                Text(
                    text = meal.name,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(14.dp)
                        .widthIn(max = 120.dp),
                    color =  MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = meal.price.toString(), modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(14.dp)
                        .widthIn(40.dp),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}