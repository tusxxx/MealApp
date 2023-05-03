package com.tusxapps.mealapp.domain.user

import com.tusxapps.mealapp.domain.meal.Meal
import kotlinx.serialization.Serializable

@Serializable
data class Cart(
    val meals: Map<Meal, Int>,
    val totalPrice: Float
)
