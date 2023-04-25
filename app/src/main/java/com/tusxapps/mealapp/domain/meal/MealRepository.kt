package com.tusxapps.mealapp.domain.meal

interface MealRepository {
    suspend fun getAll(): Result<List<Meal>>
    suspend fun createMeal(meal: Meal): Result<Unit>
    suspend fun updateMeal(meal: Meal): Result<Unit>
    suspend fun deleteMeal(meal: Meal): Result<Unit>
}