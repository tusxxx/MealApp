package com.tusxapps.mealapp.ui.main.meal.create

import android.net.Uri

data class CreateMealScreenState(
    val mealName: String = "",
    val meanDescription: String = "",
    val price: String = "",
    val errorMessage: String? = null,
    val photoUri: Uri? = null
)