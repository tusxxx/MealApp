package com.tusxapps.mealapp.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
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
        MaterialTheme.colorScheme.surface.copy(0.9f)
    )
    ElevatedCard(onClick = { onMealClick(meal) }) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 160.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(meal.imageUrl)
                        .crossfade(true).build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Box(modifier = Modifier
                    .fillMaxSize()
                    .drawWithContent {
                        drawRect(Brush.verticalGradient(colors))
                    })
            }
            Spacer(Modifier.height(4.dp))
            Text(
                text = meal.name,
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = meal.price.toString(),
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Medium,
                maxLines = 1
            )
            Spacer(Modifier.height(4.dp))
        }
    }
}