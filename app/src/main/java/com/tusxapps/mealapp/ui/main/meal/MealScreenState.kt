package com.tusxapps.mealapp.ui.main.meal

import com.tusxapps.mealapp.domain.meal.Meal
import com.tusxapps.mealapp.domain.user.User

data class MealScreenState(
    val meal: Meal? = null,
    val user: User? = null
)