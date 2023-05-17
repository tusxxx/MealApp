package com.tusxapps.mealapp.ui.auth.register

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
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterScreenState())
    val state get() = _state.asStateFlow()

    fun onRegisterClick() {
        viewModelScope.launch(Dispatchers.IO) {
            when {
                _state.value.password != _state.value.passwordVerification -> {
                    _state.update { it.copy(lce = LCEState.Failure("Пароли не совпадают")) }
                }

                _state.value.password.isBlank() || _state.value.passwordVerification.isBlank() -> {
                    _state.update { it.copy(lce = LCEState.Failure("Заполните пароли")) }
                }

                else -> {
                    userRepository.register(
                        login = _state.value.login,
                        password = _state.value.password,
                        phone = ""
                    ).onSuccess {
                        userRepository.login(_state.value.login, _state.value.password)
                        _state.update { it.copy(lce = LCEState.Success) }
                    }.onFailure { error ->
                        _state.update {
                            it.copy(
                                lce = LCEState.Failure(
                                    error.message ?: "Такой пользователь существует"
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    fun onLoginChange(newLogin: String) = _state.update {
        it.copy(login = newLogin)
    }

    fun onPasswordChange(newPassword: String) = _state.update {
        it.copy(password = newPassword)
    }

    fun onPasswordVerificationChange(newPassword: String) = _state.update {
        it.copy(passwordVerification = newPassword)
    }
}
