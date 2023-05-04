package com.tusxapps.mealapp.ui.main.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tusxapps.mealapp.domain.meal.Meal
import com.tusxapps.mealapp.domain.user.Cart
import com.tusxapps.mealapp.domain.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CartViewModel @Inject constructor(
    private val userRepo: UserRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(CartScreenState())
    val state get() = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.getCurrentUser().onSuccess { user ->
                _state.update {
                    it.copy(
                        meals = user.cart.meals,
                        totalPrice = user.cart.totalPrice,
                        isAdmin = user.isAdmin
                    )
                }
            }
        }
    }

    fun onMinusItemClick(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                val currentMealCount = _state.value.meals[meal] ?: return@launch
                val newMealCount = currentMealCount - 1
                val newMeals = _state.value.meals.toMutableMap()

                if (currentMealCount <= 1) {
                    newMeals.remove(meal)
                } else {
                    newMeals[meal] = newMealCount
                }

                val cart = Cart(meals = newMeals, totalPrice = calculatePrice(newMeals))
                userRepo.saveCartForCurrentUser(cart)

                it.copy(meals = cart.meals, totalPrice = cart.totalPrice)
            }
        }
    }

    fun onPlusItemClick(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                val currentMealCount = _state.value.meals[meal] ?: return@launch
                val newMealCount = currentMealCount + 1
                val newMeals = _state.value.meals.toMutableMap()
                newMeals[meal] = newMealCount

                val cart = Cart(meals = newMeals, totalPrice = calculatePrice(newMeals))
                userRepo.saveCartForCurrentUser(cart)

                it.copy(meals = cart.meals, totalPrice = cart.totalPrice)
            }
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