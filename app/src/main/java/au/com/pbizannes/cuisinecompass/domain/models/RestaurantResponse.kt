package au.com.pbizannes.cuisinecompass.domain.models

import com.google.gson.annotations.SerializedName

data class RestaurantsResponse(
    @SerializedName("restaurants")
    val restaurants: List<Restaurant>
)
