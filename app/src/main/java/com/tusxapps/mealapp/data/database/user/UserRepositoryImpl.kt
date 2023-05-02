package com.tusxapps.mealapp.data.database.user

import android.content.SharedPreferences
import com.google.gson.Gson
import com.tusxapps.mealapp.data.database.RestaurantDatabase
import com.tusxapps.mealapp.data.toDomain
import com.tusxapps.mealapp.domain.user.User
import com.tusxapps.mealapp.domain.user.UserRepository
import javax.inject.Inject

private const val CURRENT_USER_KEY = "currentmealuser"

class UserRepositoryImpl @Inject constructor(
    private val database: RestaurantDatabase,
    private val sharedPreferences: SharedPreferences
) : UserRepository {
    override suspend fun login(login: String, password: String): Result<Unit> {
        val users = database.UserDao().getAll()
        if (users.isEmpty()) { //TODO
            addUser()
        }
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
        val users = database.UserDao().getAll()
        return if (users.map { it.login }.contains(login)) {
            Result.failure(Exception()) //TODO UserAlreadyExists exception
        } else {
            database.UserDao().insertOne(
                UserSW(
                    login = login,
                    password = password,
                    phone = phone,
                    isAdmin = false,
                    mealIds = ""
                )
            )
            Result.success(Unit)
        }
    }

    override suspend fun getCurrentUser(): Result<User> =
        try {
            val currentUser = database.UserDao().getAll()
                .first { it.id == sharedPreferences.getInt(CURRENT_USER_KEY, 1) }
            Result.success(currentUser.toDomain(database))
        } catch (e: NoSuchElementException) {
            Result.failure(e)
        }

    private fun saveCurrentUser(userId: Int) =
        sharedPreferences.edit().putInt(CURRENT_USER_KEY, userId)

    private suspend fun addUser() = database.UserDao() //TODO удалить это
        .update(
            listOf(
                UserSW(
                    login = "Huy@gmail.com",
                    password = "ignatkotikmeow",
                    phone = "+79511669071",
                    isAdmin = false,
                    mealIds = Gson().toJson(listOf(1, 2, 3))
                )
            )
        )
}