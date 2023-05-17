//package com.tusxapps.mealapp.ui.main.meal.edit
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.tusxapps.mealapp.domain.meal.MealRepository
//import dagger.hilt.android.lifecycle.HiltViewModel
//import javax.inject.Inject
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//
//@HiltViewModel
//class EditMealViewModel @Inject constructor(
//    private val mealRepository: MealRepository,
//) : ViewModel() {
//    private val _state = MutableStateFlow(EditMealScreenState())
//    val state get() = _state.asStateFlow()
//
//    fun setMeal(mealId: Int) {
//        viewModelScope.launch {
//            mealRepository.getAll().onSuccess {
//                val meal = it.firstOrNull { it.id == mealId }
//                _state.update { it.copy(meal = meal) }
//            }
//        }
//    }
//}