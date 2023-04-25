package com.tusxapps.mealapp.domain.profile

interface UserRepository {
    suspend fun login(login: String, password: String): Result<Unit>
    suspend fun register(login: String, password: String): Result<Unit>
    suspend fun getCurrentUser(): Result<User>
    suspend fun getUserCart(): Result<Cart>
}