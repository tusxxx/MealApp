package com.tusxapps.mealapp.data.database.meal

import com.tusxapps.mealapp.data.database.RestaurantDatabase
import com.tusxapps.mealapp.data.toDomain
import com.tusxapps.mealapp.data.toSW
import com.tusxapps.mealapp.domain.meal.Meal
import com.tusxapps.mealapp.domain.meal.MealRepository

class MealRepositoryImpl(private val database: RestaurantDatabase) : MealRepository {
    override suspend fun getAll(): Result<List<Meal>> =
        try {
            val mealSWs = database.mealDao().getAll()
            if (mealSWs.isEmpty()) { //TODO
                addMeals()
            }
            Result.success(mealSWs.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun createMeal(meal: Meal): Result<Unit> =
        try {
            database.mealDao().insertOne(meal.toSW())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun updateMeal(meal: Meal): Result<Unit> =
        try {
            database.mealDao().updateMeal(meal.toSW())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun deleteMeal(meal: Meal): Result<Unit> =
        try {
            database.mealDao().deleteMeal(meal.toSW())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    private suspend fun addMeals() {
        database.mealDao().insertAll(
            listOf(
                MealSW(
                    id = 1,
                    name = "Bluedo-Huedo",
                    description = "adsasdasdasdasdasdasdsasadda",
                    price = 100.1,
                    imageUrl = "https://spoonacular.com/recipeImages/123231-556x370.jpg"
                ),
                MealSW(
                    id = 2,
                    name = "Huedo",
                    description = "adsasdasdasdasdasdasdsasadda",
                    price = 1012.1,
                    imageUrl = "https://spoonacular.com/recipeImages/3321-556x370.jpg"
                ),
                MealSW(
                    id = 3,
                    name = "Basde",
                    description = "adsasdasdasdasdasdasdsasadda",
                    price = 10.1,
                    imageUrl = "https://spoonacular.com/recipeImages/523-556x370.jpg"
                ),
                MealSW(
                    id = 4,
                    name = "Bluedo",
                    description = "adsasdasdasdasdasdasdsasadda",
                    price = 1.1,
                    imageUrl = "https://spoonacular.com/recipeImages/633-556x370.jpg"
                ),
                MealSW(
                    id = 5,
                    name = "Bluedo",
                    description = "adsasdasdasdasdasdasdsasadda",
                    price = 1300.1,
                    imageUrl = "https://spoonacular.com/recipeImages/1233-556x370.jpg"
                ),
                MealSW(
                    id = 6,
                    name = "Oliview",
                    description = "AAAA",
                    price = 1.1,
                    imageUrl = "https://spoonacular.com/recipeImages/333-556x370.jpg"
                ),
                MealSW(
                    id = 7,
                    name = "Bluedo",
                    description = "adsasdasdasdasdasdasdsasadda",
                    price = 1300.1,
                    imageUrl = "https://spoonacular.com/recipeImages/1673-556x370.jpg"
                )
            )
        )
    }
}