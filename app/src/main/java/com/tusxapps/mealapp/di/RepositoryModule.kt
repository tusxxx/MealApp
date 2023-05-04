package com.tusxapps.mealapp.di

import android.content.SharedPreferences
import com.tusxapps.mealapp.data.database.RestaurantDatabase
import com.tusxapps.mealapp.data.database.meal.MealRepositoryImpl
import com.tusxapps.mealapp.data.database.user.UserRepositoryImpl
import com.tusxapps.mealapp.data.order.OrderRepositoryImpl
import com.tusxapps.mealapp.domain.meal.MealRepository
import com.tusxapps.mealapp.domain.order.OrderRepository
import com.tusxapps.mealapp.domain.user.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideMealRepository(
        database: RestaurantDatabase,
    ): MealRepository =
        MealRepositoryImpl(
            database = database
        )

    @Provides
    @Singleton
    fun provideUserRepository(
        database: RestaurantDatabase,
        sharedPreferences: SharedPreferences,
    ): UserRepository =
        UserRepositoryImpl(
            database = database,
            sharedPreferences = sharedPreferences
        )

    @Provides
    @Singleton
    fun provideOrderRepository(
        database: RestaurantDatabase,
        userRepository: UserRepository,
    ): OrderRepository = OrderRepositoryImpl(database, userRepository)
}