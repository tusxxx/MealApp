package com.tusxapps.mealapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tusxapps.mealapp.data.database.meal.MealDao
import com.tusxapps.mealapp.data.database.meal.MealSW
import com.tusxapps.mealapp.data.database.order.OrderDao
import com.tusxapps.mealapp.data.database.order.OrderSW
import com.tusxapps.mealapp.data.database.user.UserDao
import com.tusxapps.mealapp.data.database.user.UserSW

@Database(
    entities = [MealSW::class, UserSW::class, OrderSW::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class RestaurantDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun userDao(): UserDao
    abstract fun orderDao(): OrderDao
}