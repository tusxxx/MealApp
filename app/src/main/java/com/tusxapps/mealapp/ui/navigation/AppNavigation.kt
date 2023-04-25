package com.tusxapps.mealapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.tusxapps.mealapp.ui.auth.login.LoginScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Graph.Auth.route,
        route = Graph.Root.route
    ) {
        navigation(startDestination = Screen.Login.route, route = Graph.Auth.route) {
            composable(Screen.Login.route) {
                LoginScreen(navController = navController, viewModel = hiltViewModel())
            }
            composable(Screen.Register.route) {

            }
        }

        navigation(startDestination = Screen.Home.route, route = Graph.Main.route) {
            composable(Screen.Home.route) {

            }
            composable(Screen.Cart.route) {

            }
        }
    }
}