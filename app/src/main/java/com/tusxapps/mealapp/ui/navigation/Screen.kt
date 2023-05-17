package com.tusxapps.mealapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val icon: ImageVector? = null) {

    // Main flow
    object Home : Screen("home", Icons.Filled.Home)
    object Meal : Screen("meal/{mealId}") {
        fun createRoute(mealId: Int) = "meal/$mealId"
    }
    object CreateMeal : Screen("createMeal/{mealId}") {
        fun createRoute(mealId: Int?) = "createMeal/${mealId ?: -1}"
    }

    object Cart : Screen("cart", Icons.Filled.ShoppingCart)
    object Profile : Screen("profile", Icons.Filled.Person)

    // Auth flow
    object Login : Screen("login")
    object Register : Screen("register")
    object Order : Screen("order")
}
