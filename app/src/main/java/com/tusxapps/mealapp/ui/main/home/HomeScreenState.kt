package com.tusxapps.mealapp.ui.main.home

import com.tusxapps.mealapp.domain.meal.Meal
import com.tusxapps.mealapp.domain.user.User

data class HomeScreenState(
    val currentQuery: String = "",
    val filterTag: FilterTag = FilterTag.PRICE,
    val allMeals: List<Meal> = emptyList(),
    val filteredMeals: List<Meal> = emptyList(),
    val user: User? = null
)

enum class FilterTag {
    PRICE, NAME, POPULARITY
}