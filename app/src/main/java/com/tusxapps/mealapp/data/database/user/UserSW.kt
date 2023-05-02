package com.tusxapps.mealapp.data.database.user

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tusxapps.mealapp.data.database.meal.MealSW

@Entity
data class UserSW(
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    val login: String,
    val password: String,
    val phone: String,
    val isAdmin: Boolean,
    val mealIds: String
)