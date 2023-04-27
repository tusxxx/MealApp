package com.tusxapps.mealapp.ui.navigation

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.example.compose.MealAppTheme

@Composable
fun AppEntry() {
   MealAppTheme { Surface { AppNavigation() } }
}