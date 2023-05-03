package com.tusxapps.mealapp.ui.main.order

data class OrderScreenState(
    val OrderTest: String = "Order",
    val address: String = "",
    val isSBPSelected: Boolean = false,
    val isCardSelected: Boolean = false,
    val isManualBuySelected: Boolean = true,
    val isDialogShowed: Boolean = false
)