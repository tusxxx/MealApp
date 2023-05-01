package com.tusxapps.mealapp.ui.main.cart

import com.tusxapps.mealapp.domain.meal.Meal

data class CartScreenState(
    val meals: Map<Meal, Int> = emptyMap(),
    val totalPrice: Float = 0.0f,
)