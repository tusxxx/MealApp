package com.tusxapps.mealapp.domain.user

data class User(
    val id: Int,
    val login: String,
    val password: String,
    val phone: String,
    val isAdmin: Boolean,
    val cart: Cart
)