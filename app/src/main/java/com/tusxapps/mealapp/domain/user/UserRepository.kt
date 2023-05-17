package com.tusxapps.mealapp.domain.user

interface UserRepository {
    suspend fun login(login: String, password: String): Result<Unit>
    suspend fun register(login: String, password: String, phone: String): Result<Unit>
    suspend fun getCurrentUser(): Result<User>

    suspend fun saveCartForCurrentUser(cart: Cart): Result<Unit>

    suspend fun logout(): Result<Unit>

    suspend fun clearCart(): Result<Unit>

    suspend fun getAll(): Result<List<User>>
}