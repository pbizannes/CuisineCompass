package au.com.pbizannes.cuisinecompass.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RestaurantDeal(
    val id: String,
    val discountText: String,
    val conditionText: String? = null,
    val dealsLeftText: String
) : Parcelable
