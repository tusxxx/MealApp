package com.tusxapps.mealapp.data.database.meal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert

@Dao
interface MealDao {
    @Query("SELECT * FROM mealsw")
    suspend fun getAll(): List<MealSW>

    @Insert
    suspend fun insertAll(meals: List<MealSW>)

    @Insert
    suspend fun insertOne(meal: MealSW)

    @Query("DELETE FROM mealsw")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteMeal(meal: MealSW)

    @Upsert
    suspend fun updateMeal(updatedMeal: MealSW)

    @Transaction
    suspend fun update(newMeals: List<MealSW>) {
        deleteAll()
        insertAll(newMeals)
    }
}