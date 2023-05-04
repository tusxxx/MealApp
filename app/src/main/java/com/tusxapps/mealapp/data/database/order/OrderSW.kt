package com.tusxapps.mealapp.data.database.order

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.tusxapps.mealapp.data.database.user.UserSW
import java.util.Date

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = UserSW::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("userId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class OrderSW(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Date,
    val address: String,
    val cartJson: String,
    val userId: Int,
)
