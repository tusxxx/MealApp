package com.tusxapps.mealapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.tusxapps.mealapp.data.database.meal.MealDao
import com.tusxapps.mealapp.data.database.meal.MealSW
import com.tusxapps.mealapp.data.database.user.UserDao
import com.tusxapps.mealapp.data.database.user.UserSW

@Database(
    entities = [MealSW::class, UserSW::class],
    version = 1
)
abstract class RestaurantDatabase : RoomDatabase() {
    abstract fun MealDao(): MealDao
    abstract fun UserDao(): UserDao
}