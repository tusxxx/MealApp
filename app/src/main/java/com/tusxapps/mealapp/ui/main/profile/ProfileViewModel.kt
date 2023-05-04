package com.tusxapps.mealapp.ui.main.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tusxapps.mealapp.domain.order.OrderRepository
import com.tusxapps.mealapp.domain.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val orderRepo: OrderRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileScreenState())
    val state get() = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.getCurrentUser().onSuccess { user ->
                _state.update { it.copy(user = user) }
            }
            orderRepo.getCurrentUsersOrders().onSuccess { orders ->
                _state.update { it.copy(orders = orders) }
                Log.d("TAG", "asd: ${orders}")
            }
        }
    }

    fun onExitClick() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.logout()
            _state.update { ProfileScreenState() }
        }
    }
}