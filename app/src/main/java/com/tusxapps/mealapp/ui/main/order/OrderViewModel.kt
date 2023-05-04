package com.tusxapps.mealapp.ui.main.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tusxapps.mealapp.domain.order.Order
import com.tusxapps.mealapp.domain.order.OrderRepository
import com.tusxapps.mealapp.domain.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.IllegalStateException
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
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
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getCurrentUser()
                .onSuccess {
                    orderRepository.createOrder(Order(Date(), it.cart, "NewAdress", it.id))
                        .onSuccess {
                            _state.update {
                                it.copy(isDialogShowed = true)
                            }
                        }
                        .onFailure {
                            if (it is IllegalStateException) {
                                _state.update {
                                    it.copy(isHaveActiveOrders = true)
                                }
                            }
                        }
                }
        }
    }
}