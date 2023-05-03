package com.tusxapps.mealapp.ui.main.order

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class OrderViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(OrderScreenState())
    val state get() = _state.asStateFlow()

    fun onAddressChange(newAddress: String) = _state.update { it.copy(address = newAddress) }

    fun onSBPClick() = _state.update {
        it.copy(isSBPSelected = true, isCardSelected = false, isManualBuySelected = false)
    }

    fun onCardClick() = _state.update {
        it.copy(isSBPSelected = false, isCardSelected = true, isManualBuySelected = false)
    }

    fun onManualBuyClick() = _state.update {
        it.copy(isSBPSelected = false, isCardSelected = false, isManualBuySelected = true)
    }

    fun onOrderCLick() {
        _state.update {
            it.copy(isDialogShowed = true)
        }
    }
}