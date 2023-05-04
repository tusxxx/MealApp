package com.tusxapps.mealapp.data.database.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserSW(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val login: String,
    val password: String,
    val phone: String,
    val isAdmin: Boolean,
    val cartJson: String
)