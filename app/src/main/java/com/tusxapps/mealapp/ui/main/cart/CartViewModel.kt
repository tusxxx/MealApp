package com.tusxapps.mealapp.ui.main.cart

import androidx.lifecycle.ViewModel
import com.tusxapps.mealapp.domain.meal.Meal
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(CartScreenState())
    val state get() = _state.asStateFlow()

    init {
        _state.update {
            val newMeals = mapOf(
                Meal(
                    "Baranka",
                    "Desc",
                    1112.12,
                    "https://picsum.photos/1920/1080"
                ) to 1
            )
            it.copy(
                meals = newMeals,
                totalPrice = calculatePrice(newMeals)
            )
        }
    }

    fun onMinusItemClick(meal: Meal) {
        _state.update {
            val currentMealCount = _state.value.meals[meal] ?: return
            val newMealCount = currentMealCount - 1
            val newMeals = _state.value.meals.toMutableMap()

            if (currentMealCount <= 1) {
                newMeals.remove(meal)
            } else {
                newMeals[meal] = newMealCount
            }

            it.copy(meals = newMeals, totalPrice = calculatePrice(newMeals))
        }
    }

    fun onPlusItemClick(meal: Meal) {
        _state.update {
            val currentMealCount = _state.value.meals[meal] ?: return
            val newMealCount = currentMealCount + 1
            val newMeals = _state.value.meals.toMutableMap()
            newMeals[meal] = newMealCount

            it.copy(meals = newMeals, totalPrice = calculatePrice(newMeals))
        }
    }

    private fun calculatePrice(newMeals: Map<Meal, Int>): Float {
        var newTotalPrice = 0f
        newMeals.forEach {
            newTotalPrice += (it.key.price * it.value).toFloat()
        }
        return newTotalPrice
    }
}