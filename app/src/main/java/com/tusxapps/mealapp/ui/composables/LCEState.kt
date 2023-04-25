package com.tusxapps.mealapp.ui.composables

sealed class LCEState {
    object Success : LCEState()
    data class Failure(val message: String) : LCEState()
    object Loading : LCEState()
    object Dormant : LCEState()
}
