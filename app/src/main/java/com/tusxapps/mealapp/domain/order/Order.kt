package com.tusxapps.mealapp.domain.order

import com.tusxapps.mealapp.domain.user.Cart
import java.util.Date

data class Order(
    val date: Date,
    val cart: Cart,
    val address: String,
    val userId: Int,
    val isAgreed: Boolean
)
