package com.tusxapps.mealapp.di

import android.content.Context
import androidx.room.Room
import com.tusxapps.mealapp.data.database.RestaurantDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): RestaurantDatabase =
        Room.databaseBuilder(
            context,
            RestaurantDatabase::class.java,
            "restaurant"
        ).build()
}