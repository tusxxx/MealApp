package com.tusxapps.mealapp.data.order

import com.tusxapps.mealapp.data.database.RestaurantDatabase
import com.tusxapps.mealapp.data.database.order.OrderSW
import com.tusxapps.mealapp.data.toDomain
import com.tusxapps.mealapp.data.toSW
import com.tusxapps.mealapp.domain.order.Order
import com.tusxapps.mealapp.domain.order.OrderRepository
import com.tusxapps.mealapp.domain.user.UserRepository
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class OrderRepositoryImpl @Inject constructor(
    private val database: RestaurantDatabase,
    private val userRepository: UserRepository,
) : OrderRepository {
    private val json = Json { allowStructuredMapKeys = true }

    init {
        CoroutineScope(Job()).launch {
            try {
                val orders = database.orderDao().getAll()
                if (orders.isEmpty()) {
                    database.orderDao().insert(
                        OrderSW(
                            date = Date(),
                            address = "Bruh",
                            cartJson = json.encodeToString(
                                userRepository.getCurrentUser().getOrThrow().cart
                            ),
                            userId = userRepository.getCurrentUser().getOrThrow().id
                        )
                    )
                }
            } catch (e: Exception) {
            }
        }
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
            if (database.orderDao().getAll().isNotEmpty()) throw IllegalStateException("")
            database.orderDao().deleteAll()
            database.orderDao().insert(order.toSW())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

}

