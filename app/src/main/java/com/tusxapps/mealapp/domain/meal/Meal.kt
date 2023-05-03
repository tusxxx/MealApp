package com.tusxapps.mealapp.domain.meal

import kotlinx.serialization.Serializable

@Serializable
data class Meal(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String
)