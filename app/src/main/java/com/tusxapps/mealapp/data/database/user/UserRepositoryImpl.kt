package com.tusxapps.mealapp.data.database.user

import android.content.SharedPreferences
import androidx.core.content.edit
import com.tusxapps.mealapp.data.database.RestaurantDatabase
import com.tusxapps.mealapp.data.toDomain
import com.tusxapps.mealapp.data.toSW
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
                    cartJson = json.encodeToString(Cart(emptyMap(), 0f))
                )
            )
            Result.success(Unit)
        }
    }

    override suspend fun getCurrentUser(): Result<User> {
        return try {
            val allUsers = database.userDao().getAll()
            val currentUserId = sharedPreferences.getInt(CURRENT_USER_KEY, -1)
            val currentUser = allUsers
                .first { it.id == currentUserId }
            Result.success(currentUser.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
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

    override suspend fun logout(): Result<Unit> =
        try {
            sharedPreferences.edit {
                putInt(CURRENT_USER_KEY, -1)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun clearCart(): Result<Unit> =
        try {
            database
                .userDao()
                .update(
                    getCurrentUser()
                        .getOrThrow()
                        .copy(cart = Cart(emptyMap(), 0f))
                        .toSW()
                )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }


    private fun saveCurrentUser(userId: Int) =
        sharedPreferences.edit().putInt(CURRENT_USER_KEY, userId).apply()

    private suspend fun addUser() {
        saveCurrentUser(1)
        val cart = Cart(emptyMap(), 0f)
        database.userDao().apply {
            insertOne(
                UserSW(
                    id = 1,
                    login = "",
                    password = "",
                    phone = "",
                    isAdmin = false,
                    cartJson = json.encodeToString(cart)
                )
            )
            insertOne(
                UserSW(
                    id = 2,
                    login = "Efim",
                    password = "asdfg123",
                    phone = "",
                    isAdmin = true,
                    cartJson = json.encodeToString(cart)
                )
            )
            insertOne(
                UserSW(
                    id = 3,
                    login = "Tatyana",
                    password = "qwer123",
                    phone = "",
                    isAdmin = true,
                    cartJson = json.encodeToString(cart)
                )
            )
            insertOne(
                UserSW(
                    id = 4,
                    login = "test",
                    password = "test",
                    phone = "",
                    isAdmin = false,
                    cartJson = json.encodeToString(cart)
                )
            )
        }
    }
}