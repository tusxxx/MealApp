package com.tusxapps.mealapp.ui.main.meal

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class MealViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(MealScreenState())
    val state get() = _state.asStateFlow()

    fun setMeal(mealName: String) {
        _state.update { it.copy(mealName = mealName) }
    }
}