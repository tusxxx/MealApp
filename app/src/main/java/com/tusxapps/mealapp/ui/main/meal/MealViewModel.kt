package com.tusxapps.mealapp.ui.main.meal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tusxapps.mealapp.data.calculatePrice
import com.tusxapps.mealapp.domain.meal.MealRepository
import com.tusxapps.mealapp.domain.user.Cart
import com.tusxapps.mealapp.domain.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val mealsRepository: MealRepository,
    private val usersRepository: UserRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MealScreenState())
    val state get() = _state.asStateFlow()

    fun setMeal(mealId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            mealsRepository.getAll().onSuccess {
                it.find { it.id == mealId }?.let { meal ->
                    _state.update { it.copy(meal = meal) }
                }
            }
        }
    }

    fun onAddToCartClick() {
        viewModelScope.launch(Dispatchers.IO) {
            usersRepository.getCurrentUser()
                .onSuccess { user ->
                    val meal = _state.value.meal ?: return@launch
                    val newMeals = user.cart.meals.toMutableMap()
                    if (!newMeals.keys.contains(meal)) {
                        newMeals[meal] = 1
                    } else {
                        val currentMealCount = newMeals[meal] ?: return@launch
                        val newMealCount = currentMealCount + 1
                        newMeals[meal] = newMealCount
                    }
                    usersRepository.saveCartForCurrentUser(Cart(newMeals, calculatePrice(newMeals)))
                }
        }
    }
}