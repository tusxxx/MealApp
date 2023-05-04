package com.tusxapps.mealapp.ui.main.cart

import com.tusxapps.mealapp.domain.meal.Meal
import com.tusxapps.mealapp.domain.user.User

data class CartScreenState(
    val meals: Map<Meal, Int> = emptyMap(),
    val totalPrice: Float = 0.0f,
    val isAdmin: Boolean = false,
    val user: User? = null
)