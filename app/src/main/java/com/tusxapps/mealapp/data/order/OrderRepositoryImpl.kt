package com.tusxapps.mealapp.data.order

import androidx.compose.ui.graphics.vector.RenderVectorGroup
import com.tusxapps.mealapp.data.database.RestaurantDatabase
import com.tusxapps.mealapp.data.toDomain
import com.tusxapps.mealapp.data.toSW
import com.tusxapps.mealapp.domain.order.Order
import com.tusxapps.mealapp.domain.order.OrderRepository
import com.tusxapps.mealapp.domain.user.UserRepository
import javax.inject.Inject
import kotlinx.serialization.json.Json

class OrderRepositoryImpl @Inject constructor(
    private val database: RestaurantDatabase,
    private val userRepository: UserRepository,
) : OrderRepository {
    private val json = Json { allowStructuredMapKeys = true }

    init {
//        CoroutineScope(Job()).launch {
//            try {
//                val orders = database.orderDao().getAll()
//                if (orders.isEmpty()) {
//                    database.orderDao().insert(
//                        OrderSW(
//                            date = Date(),
//                            address = "Bruh",
//                            cartJson = json.encodeToString(
//                                userRepository.getCurrentUser().getOrThrow().cart
//                            ),
//                            userId = userRepository.getCurrentUser().getOrThrow().id
//                        )
//                    )
//                }
//            } catch (e: Exception) {
//            }
//        }
    }

    override suspend fun getCurrentUsersOrders(): Result<List<Order>> {
        return try {
            val orders = database.orderDao().getAll()
            val currentUsersOrders = orders
                .filter { it.userId == userRepository.getCurrentUser().getOrThrow().id }
                .map { it.toDomain() }
            Result.success(currentUsersOrders)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createOrder(order: Order): Result<Unit> =
        try {
            if (database.orderDao().getAll().any { it.userId == order.userId })
                throw IllegalStateException("")
            userRepository.clearCart()
//            database.orderDao().deleteAll()
            database.orderDao().insert(order.toSW())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun agreeOrder(userId: Int): Result<Unit> =
        try {
            val order = database.orderDao().getAll().first { it.userId == userId }
            database.orderDao().update(order.copy(isAgreed = true))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun getAll(): Result<List<Order>> =
        try {
            val orders = database.orderDao().getAll().map { it.toDomain() }
            Result.success(orders)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun deleteOrder(userId: Int): Result<Unit> =
        try {
            database.orderDao().getAll().firstOrNull { it.userId == userId }?.let {
                database.orderDao().delete(it)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

}

