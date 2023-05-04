package com.tusxapps.mealapp.ui.main.home

import com.tusxapps.mealapp.domain.meal.Meal

data class HomeScreenState(
    val currentQuery: String = "",
    val filterTag: FilterTag = FilterTag.PRICE,
    val allMeals: List<Meal> = emptyList(),
    val filteredMeals: List<Meal> = emptyList()
)

enum class FilterTag {
    PRICE, NAME, POPULARITY
}