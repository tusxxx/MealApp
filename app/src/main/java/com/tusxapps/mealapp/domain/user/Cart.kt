package com.tusxapps.mealapp.domain.user

import com.tusxapps.mealapp.domain.meal.Meal

data class Cart(
    val meals: List<Meal>,
    val totalPrice: Double
)
