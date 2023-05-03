package com.tusxapps.mealapp.ui.main.meal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tusxapps.mealapp.domain.meal.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MealViewModel @Inject constructor(
    private val mealsRepository: MealRepository,
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

    }
}