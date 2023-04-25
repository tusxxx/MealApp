package com.tusxapps.mealapp.ui.auth.login

import com.tusxapps.mealapp.ui.composables.LCEState

data class LoginScreenState(
    val login: String = "",
    var loginErrorMessage: String? = null,
    val password: String = "",
    val passwordErrorMessage: String? = null,
    val authErrorMessage: String? = null,
    val lce: LCEState = LCEState.Dormant
)