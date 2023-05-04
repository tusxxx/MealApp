package com.tusxapps.mealapp.ui.main.profile

import com.tusxapps.mealapp.domain.order.Order
import com.tusxapps.mealapp.domain.user.User

data class ProfileScreenState(
    val user: User? = null,
    val orders: List<Order> = emptyList(),
)