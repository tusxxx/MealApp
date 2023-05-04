package com.tusxapps.mealapp.data.database.user

import android.content.SharedPreferences
import com.tusxapps.mealapp.data.database.RestaurantDatabase
import com.tusxapps.mealapp.data.toDomain
import com.tusxapps.mealapp.data.toSW
import com.tusxapps.mealapp.domain.meal.Meal
import com.tusxapps.mealapp.domain.user.Cart
import com.tusxapps.mealapp.domain.user.User
import com.tusxapps.mealapp.domain.user.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val CURRENT_USER_KEY = "currentmealuser"

class UserRepositoryImpl @Inject constructor(
    private val database: RestaurantDatabase,
    private val sharedPreferences: SharedPreferences,
) : UserRepository {
    private val json = Json { allowStructuredMapKeys = true }
    init {
        CoroutineScope(Job()).launch {
            val users = database.userDao().getAll()
            if (users.isEmpty()) { //TODO
                addUser()
            }
        }
    }

    override suspend fun login(login: String, password: String): Result<Unit> {
        val users = database.userDao().getAll()
        return if (users.map { it.login }.contains(login)) {
            val currentUser = users.first { it.login == login }
            if (currentUser.password == password) {
                saveCurrentUser(currentUser.id)
                Result.success(Unit)
            } else {
                Result.failure(Exception()) //TODO WrongPassword exception
            }
        } else {
            Result.failure(Exception()) //TODO UserNotFound exception
        }
    }

    override suspend fun register(login: String, password: String, phone: String): Result<Unit> {
        val users = database.userDao().getAll()
        return if (users.map { it.login }.contains(login)) {
            Result.failure(Exception()) //TODO UserAlreadyExists exception
        } else {
            database.userDao().insertOne(
                UserSW(
                    login = login,
                    password = password,
                    phone = phone,
                    isAdmin = false,
                    cartJson = ""
                )
            )
            Result.success(Unit)
        }
    }

    override suspend fun getCurrentUser(): Result<User> =
        try {
            val currentUser = database.userDao().getAll()
                .first { it.id == sharedPreferences.getInt(CURRENT_USER_KEY, 1) }
            Result.success(currentUser.toDomain())
        } catch (e: NoSuchElementException) {
            Result.failure(e)
        }

    override suspend fun saveCartForCurrentUser(cart: Cart): Result<Unit> {
        return try {
            val currentUser = getCurrentUser().getOrThrow().toSW()
            database.userDao().update(currentUser.copy(cartJson = json.encodeToString(cart)))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun saveCurrentUser(userId: Int) =
        sharedPreferences.edit().putInt(CURRENT_USER_KEY, userId)

    private suspend fun addUser() {
        val meals = database.mealDao().getAll()
            .map {
                it.toDomain()
            }
        val mapMeals = mapOf(meals.first() to 1, meals[2] to 4)
        val cart = Cart(mapMeals, calculatePrice(mapMeals))
        database.userDao() //TODO удалить это
            .insertOne(
                UserSW(
                    id = 1,
                    login = "Huy@gmail.com",
                    password = "ignatkotikmeow",
                    phone = "+79511669071",
                    isAdmin = false,
                    cartJson = json.encodeToString(cart)
                )
            )
        saveCurrentUser(1)
    }

    private fun calculatePrice(newMeals: Map<Meal, Int>): Float {
        var newTotalPrice = 0f
        newMeals.forEach {
            newTotalPrice += (it.key.price * it.value).toFloat()
        }
        return newTotalPrice
    }
}