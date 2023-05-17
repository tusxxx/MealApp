package com.tusxapps.mealapp.ui.main.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tusxapps.mealapp.data.calculatePrice
import com.tusxapps.mealapp.domain.meal.Meal
import com.tusxapps.mealapp.domain.order.Order
import com.tusxapps.mealapp.domain.order.OrderRepository
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
class CartViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val orderRepository: OrderRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(CartScreenState())
    val state get() = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            updateState()
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

    fun onAgreeClick(order: Order) {
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.agreeOrder(order.userId)
            updateState()
        }
    }

    fun onDisagreeClick(order: Order) {
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.deleteOrder(order.userId)
            updateState()
        }
    }

    private suspend fun updateState() {
        userRepo.getCurrentUser().onSuccess { user ->
            _state.update {
                it.copy(
                    meals = user.cart.meals,
                    totalPrice = user.cart.totalPrice,
                    isAdmin = user.isAdmin
                )
            }
        }
        if (_state.value.isAdmin) {
            orderRepository.getAll().onSuccess { orders ->
                _state.update { it.copy(orders = orders) }
            }
        }
    }

    suspend fun getUserPhone(userId: Int): String {
        return userRepo.getAll().getOrDefault(emptyList())
            .firstOrNull { it.id == userId }?.login.orEmpty()
    }
}