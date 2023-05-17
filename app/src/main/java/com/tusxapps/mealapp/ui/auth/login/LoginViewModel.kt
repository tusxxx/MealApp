package com.tusxapps.mealapp.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tusxapps.mealapp.domain.user.UserRepository
import com.tusxapps.mealapp.ui.composables.LCEState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepo: UserRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(LoginScreenState())
    val state get() = _state.asStateFlow()

    fun onLoginChange(newLogin: String) {
        _state.update { it.copy(login = newLogin) }
    }

    fun onPasswordChange(newPassword: String) {
        _state.update { it.copy(password = newPassword) }
    }

    fun onLoginClick() {
        viewModelScope.launch(Dispatchers.IO) {
            with(_state.value) {
                userRepo.login(login, password)
                    .onSuccess {
                        _state.update { it.copy(lce = LCEState.Success) }
                    }.onFailure {
                        _state.update { it.copy(lce = LCEState.Failure("Неверный логин / пароль")) }
                    }
            }
        }
    }
}