package com.tusxapps.mealapp.data.database.order

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OrderDao {
    @Query("SELECT * FROM ordersw")
    suspend fun getAll(): List<OrderSW>

    @Insert
    suspend fun insert(vararg order: OrderSW)

    @Query("DELETE FROM ordersw")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteMeal(order: OrderSW)
}