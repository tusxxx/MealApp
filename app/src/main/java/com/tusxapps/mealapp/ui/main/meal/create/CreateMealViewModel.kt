package com.tusxapps.mealapp.ui.main.meal.create

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
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
import kotlinx.coroutines.withContext

@HiltViewModel
class CreateMealViewModel @Inject constructor(
    private val mealRepository: MealRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(CreateMealScreenState())
    val state get() = _state.asStateFlow()
    private var _mealId: Int? by mutableStateOf(null)

    fun onPriceChange(newPrice: String) {
        _state.update {
            it.copy(price = newPrice)
        }
    }

    fun onNameChange(newName: String) {
        _state.update {
            it.copy(mealName = newName)
        }
    }

    fun onDescChange(newDesc: String) {
        _state.update {
            it.copy(meanDescription = newDesc)
        }
    }

    fun onAddClick(onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            with(_state.value) {
                if (mealName.isBlank()) {
                    _state.update { it.copy(errorMessage = "Название пустое") }
                    return@launch
                }
                if (meanDescription.isBlank()) {
                    _state.update { it.copy(errorMessage = "Описание пустое") }
                    return@launch
                }
                if (price.isBlank()) {
                    _state.update { it.copy(errorMessage = "Цена не указана") }
                    return@launch
                }
                if (photoUri == null) {
                    _state.update { it.copy(errorMessage = "Нет фотографии") }
                    return@launch
                }
                if (_mealId == null) {
                    mealRepository.createMeal(
                        mealName,
                        meanDescription,
                        price.toFloatOrNull() ?: return@launch,
                        photoUri.toString()
                    )
                } else {
                    mealRepository
                        .getAll()
                        .getOrElse { emptyList() }
                        .firstOrNull { it.id == _mealId }
                        ?.let { meal ->
                            mealRepository.updateMeal(
                                meal.copy(
                                    name = _state.value.mealName,
                                    description = _state.value.meanDescription,
                                    price = _state.value.price.toDoubleOrNull() ?: 0.0,
                                    imageUrl = _state.value.photoUri.toString()
                                )
                            )
                        }
                }
                withContext(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    fun onPhotoAdd(uri: Uri) {
        _state.update { it.copy(photoUri = uri) }
    }

    fun setMeal(mealId: Int) {
        viewModelScope.launch {
            mealRepository.getAll().onSuccess {
                it.firstOrNull { it.id == mealId }?.let { meal ->
                    _state.update {
                        it.copy(
                            mealName = meal.name,
                            meanDescription = meal.description,
                            price = meal.price.toString(),
                            photoUri = meal.imageUrl.toUri()
                        )
                    }
                    _mealId = meal.id
                }
            }
        }
    }
}