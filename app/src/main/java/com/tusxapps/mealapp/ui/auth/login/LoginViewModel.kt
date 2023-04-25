package com.tusxapps.mealapp.ui.auth.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(LoginScreenState())
    val state get() = _state.asStateFlow()
}