package com.tusxapps.mealapp.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tusxapps.mealapp.data.database.RestaurantDatabase
import com.tusxapps.mealapp.data.database.meal.MealSW
import com.tusxapps.mealapp.data.database.user.UserSW
import com.tusxapps.mealapp.domain.meal.Meal
import com.tusxapps.mealapp.domain.user.Cart
import com.tusxapps.mealapp.domain.user.User
import java.lang.reflect.Type


suspend fun UserSW.toDomain(database: RestaurantDatabase) = User(
    id = id,
    login = login,
    password = password,
    phone = phone,
    isAdmin = isAdmin,
    cart = mealIds.toCart(database)
)

fun MealSW.toDomain() = Meal(
    id = id,
    name = name,
    description = description,
    price = price,
    imageUrl = imageUrl
)

fun Meal.toSW() = MealSW(
    id = id,
    name = name,
    description = description,
    price = price,
    imageUrl = imageUrl
)

suspend fun String.toCart(database: RestaurantDatabase): Cart {
    val type: Type = object : TypeToken<List<String>>() {}.type
    val mealIds = Gson().fromJson<List<String>>(this, type)
    val meals = database.MealDao().getAll()
        .filter { mealIds.contains(it.id.toString()) }
        .map { it.toDomain() }
    return Cart(
        meals = meals,
        totalPrice = meals.sumOf { it.price }
    )
}

fun Cart.toMealIds() = this.meals.map { it.id }