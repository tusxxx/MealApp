package com.tusxapps.mealapp.domain.order

interface OrderRepository {
    suspend fun getCurrentUsersOrders(): Result<List<Order>>
    suspend fun createOrder(order: Order): Result<Unit>
}