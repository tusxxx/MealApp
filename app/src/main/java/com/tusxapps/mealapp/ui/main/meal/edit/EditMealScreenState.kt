package com.tusxapps.mealapp.ui.main.meal.edit

import android.net.Uri
import com.tusxapps.mealapp.domain.meal.Meal

data class EditMealScreenState(
    val mealName: String = "",
    val meanDescription: String = "",
    val price: String = "",
    val errorMessage: String? = null,
    val photoUri: Uri? = null
)