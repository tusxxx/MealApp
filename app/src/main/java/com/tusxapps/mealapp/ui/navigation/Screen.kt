package com.tusxapps.mealapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val icon: ImageVector? = null) {

    // Main flow
    object Home : Screen("home", Icons.Filled.Home)
    object Cart : Screen("cart", Icons.Filled.ShoppingCart)

    // Auth flow
    object Login : Screen("login")
    object Register : Screen("register")
}
