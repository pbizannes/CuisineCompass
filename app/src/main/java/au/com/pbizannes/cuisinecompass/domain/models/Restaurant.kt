package au.com.pbizannes.cuisinecompass.domain.models

import com.google.gson.annotations.SerializedName

data class Restaurant(
    @SerializedName("objectId")
    val objectId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("address1")
    val address1: String,

    @SerializedName("suburb")
    val suburb: String,

    @SerializedName("cuisines")
    val cuisines: List<String>,

    @SerializedName("imageLink")
    val imageLink: String?, // Marked as nullable

    @SerializedName("open")
    val openTime: String,

    @SerializedName("close")
    val closeTime: String,

    @SerializedName("deals")
    val deals: List<Deal>
)
