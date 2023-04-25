package com.tusxapps.mealapp.ui.navigation

sealed class Graph(val route: String) {
    object Root: Graph("root")
    object Auth: Graph("auth")
    object Main: Graph("main")
}
