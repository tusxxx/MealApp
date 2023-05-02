package com.tusxapps.mealapp.di

import android.content.SharedPreferences
import com.tusxapps.mealapp.data.database.RestaurantDatabase
import com.tusxapps.mealapp.data.database.meal.MealRepositoryImpl
import com.tusxapps.mealapp.data.database.user.UserRepositoryImpl
import com.tusxapps.mealapp.domain.meal.MealRepository
import com.tusxapps.mealapp.domain.user.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideMealRepository(
        database: RestaurantDatabase
    ): MealRepository =
        MealRepositoryImpl(
            database = database
        )

    @Provides
    fun provideUserRepository(
        database: RestaurantDatabase,
        sharedPreferences: SharedPreferences
    ): UserRepository =
        UserRepositoryImpl(
            database = database,
            sharedPreferences = sharedPreferences
        )
}