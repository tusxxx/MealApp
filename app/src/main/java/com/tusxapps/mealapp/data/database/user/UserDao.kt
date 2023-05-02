package com.tusxapps.mealapp.data.database.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface UserDao {
    @Query("SELECT * FROM usersw")
    suspend fun getAll(): List<UserSW>

    @Insert
    suspend fun insertAll(users: List<UserSW>)

    @Insert
    suspend fun insertOne(user: UserSW)

    @Query("DELETE FROM usersw")
    suspend fun deleteAll()

    @Transaction
    suspend fun update(newUsers: List<UserSW>) {
        deleteAll()
        insertAll(newUsers)
    }
}