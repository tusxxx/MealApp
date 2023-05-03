package com.tusxapps.mealapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tusxapps.mealapp.ui.auth.login.LoginScreen
import com.tusxapps.mealapp.ui.main.cart.CartScreen
import com.tusxapps.mealapp.ui.main.home.HomeScreen
import com.tusxapps.mealapp.ui.main.meal.MealScreen
import com.tusxapps.mealapp.ui.main.order.OrderScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    Scaffold(bottomBar = { AppBottomBar(navController = navController) }) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Graph.Main.route,
            route = Graph.Root.route,
            modifier = Modifier.padding(paddingValues)
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
                    HomeScreen(navController = navController, viewModel = hiltViewModel())
                }
                composable(Screen.Cart.route) {
                    CartScreen(navController = navController, viewModel = hiltViewModel())
                }
                composable(
                    route = Screen.Meal.route,
                    arguments = listOf(navArgument("mealId") { type = NavType.IntType })
                ) {
                    it.arguments?.getInt("mealId")?.let { mealName ->
                        MealScreen(
                            navController = navController,
                            viewModel = hiltViewModel(),
                            mealId = mealName
                        )
                    }
                }
                composable(
                    route = Screen.Order.route
                ) {
                    OrderScreen(navController = navController, viewModel = hiltViewModel())
                }
            }
        }
    }
}