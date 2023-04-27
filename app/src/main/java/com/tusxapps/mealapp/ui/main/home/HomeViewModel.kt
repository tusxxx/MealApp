package com.tusxapps.mealapp.ui.main.home

import androidx.lifecycle.ViewModel
import com.tusxapps.mealapp.domain.meal.Meal
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val meals = listOf(
        Meal(
            "Bluedo-Huedo",
            "adsasdasdasdasdasdasdsasadda",
            100.1,
            "https://spoonacular.com/recipeImages/123231-556x370.jpg"
        ),
        Meal(
            "Huedo",
            "adsasdasdasdasdasdasdsasadda",
            1012.1,
            "https://spoonacular.com/recipeImages/3321-556x370.jpg"
        ),
        Meal(
            "Basde",
            "adsasdasdasdasdasdasdsasadda",
            10.1,
            "https://spoonacular.com/recipeImages/523-556x370.jpg"
        ),
        Meal(
            "Bluedo",
            "adsasdasdasdasdasdasdsasadda",
            1.1,
            "https://spoonacular.com/recipeImages/633-556x370.jpg"
        ),
        Meal(
            "Bluedo",
            "adsasdasdasdasdasdasdsasadda",
            1300.1,
            "https://spoonacular.com/recipeImages/1233-556x370.jpg"
        ),
        Meal(
            "Oliview",
            "AAAA",
            1.1,
            "https://spoonacular.com/recipeImages/333-556x370.jpg"
        ),
        Meal(
            "Bluedo",
            "adsasdasdasdasdasdasdsasadda",
            1300.1,
            "https://spoonacular.com/recipeImages/1673-556x370.jpg"
        )
    )
    private val _state = MutableStateFlow(HomeScreenState(allMeals = meals, filteredMeals = meals))
    val state get() = _state.asStateFlow()

    fun onSearchValueChange(newQuery: String) = _state.update {
        val filteredMeals = _state.value.allMeals.filter {
            it.name.lowercase(Locale.getDefault()).contains(newQuery)
        }
        it.copy(currentQuery = newQuery, filteredMeals = filteredMeals)
    }


    fun onFilterSelect(newTag: FilterTag) {
        _state.update {
            val filteredMeals = when (newTag) {
                FilterTag.PRICE -> meals.sortedBy { it.price }
                FilterTag.NAME -> meals.sortedBy { it.name }
                FilterTag.POPULARITY -> meals.sortedBy { it.name }
            }.filter { meal ->
                meal.name.lowercase(Locale.getDefault()).contains(it.currentQuery)
            }
            it.copy(filterTag = newTag, filteredMeals = filteredMeals)
        }
    }
}