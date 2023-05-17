package com.tusxapps.mealapp.ui.auth.register

import com.tusxapps.mealapp.ui.composables.LCEState

data class RegisterScreenState(
    val RegisterTest: String = "Register",
    val login: String = "",
    val password: String = "",
    val passwordVerification: String = "",
    val lce: LCEState = LCEState.Dormant,
    val errorMessage: String = "",
)