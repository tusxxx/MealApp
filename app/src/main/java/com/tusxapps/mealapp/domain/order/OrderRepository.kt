package com.tusxapps.mealapp.domain.order

interface OrderRepository {
    suspend fun getCurrentUsersOrders(): Result<List<Order>>
    suspend fun createOrder(order: Order): Result<Unit>

    suspend fun agreeOrder(orderId: Int): Result<Unit>
    suspend fun getAll(): Result<List<Order>>
    suspend fun deleteOrder(userId: Int): Result<Unit>
}