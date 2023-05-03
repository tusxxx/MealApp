package com.tusxapps.mealapp.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tusxapps.mealapp.domain.meal.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mealRepo: MealRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())
    val state get() = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            mealRepo.getAll().onSuccess { meals ->
                _state.update { it.copy(filteredMeals = meals, allMeals = meals) }
            }
        }
    }

    fun onSearchValueChange(newQuery: String) = _state.update {
        val filteredMeals = _state.value.allMeals.filter {
            it.name.lowercase(Locale.getDefault()).contains(newQuery.lowercase(Locale.getDefault()))
        }
        it.copy(currentQuery = newQuery, filteredMeals = filteredMeals)
    }


    fun onFilterSelect(newTag: FilterTag) {
        _state.update {
            val filteredMeals = when (newTag) {
                FilterTag.PRICE -> it.allMeals.sortedBy { it.price }
                FilterTag.NAME -> it.allMeals.sortedBy { it.name }
                FilterTag.POPULARITY -> it.allMeals.sortedBy { it.name }
            }.filter { meal ->
                meal.name.lowercase(Locale.getDefault()).contains(it.currentQuery)
            }
            it.copy(filterTag = newTag, filteredMeals = filteredMeals)
        }
    }
}