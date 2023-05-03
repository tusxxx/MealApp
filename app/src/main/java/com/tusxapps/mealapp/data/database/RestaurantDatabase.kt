package com.tusxapps.mealapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tusxapps.mealapp.data.database.meal.MealDao
import com.tusxapps.mealapp.data.database.meal.MealSW
import com.tusxapps.mealapp.data.database.user.UserDao
import com.tusxapps.mealapp.data.database.user.UserSW

@Database(
    entities = [MealSW::class, UserSW::class],
    version = 1
)
abstract class RestaurantDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun userDao(): UserDao
}