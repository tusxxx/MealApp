package com.tusxapps.mealapp.data.database.meal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MealSW(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String
)