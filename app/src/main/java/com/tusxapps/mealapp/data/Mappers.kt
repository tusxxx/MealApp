package com.tusxapps.mealapp.data

import com.tusxapps.mealapp.data.database.meal.MealSW
import com.tusxapps.mealapp.data.database.user.UserSW
import com.tusxapps.mealapp.domain.meal.Meal
import com.tusxapps.mealapp.domain.user.User
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val json = Json { allowStructuredMapKeys = true }
fun UserSW.toDomain() = User(
    id = id,
    login = login,
    password = password,
    phone = phone,
    isAdmin = isAdmin,
    cart = json.decodeFromString(cartJson)
)

fun User.toSW() = UserSW(
    id = id,
    login = login,
    password = password,
    phone = phone,
    isAdmin = isAdmin,
    cartJson = json.encodeToString(cart)
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

fun calculatePrice(newMeals: Map<Meal, Int>): Float {
    var newTotalPrice = 0f
    newMeals.forEach {
        newTotalPrice += (it.key.price * it.value).toFloat()
    }
    return newTotalPrice
}