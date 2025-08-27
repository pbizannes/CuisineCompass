package au.com.pbizannes.cuisinecompass.domain.models

import com.google.gson.annotations.SerializedName

data class Deal(
    @SerializedName("objectId")
    val objectId: String,

    @SerializedName("discount")
    val discount: String, // Keep as String, or use custom TypeAdapter for Int/Double

    @SerializedName("dineIn")
    val dineIn: String, // Keep as String, or use custom TypeAdapter for Boolean

    @SerializedName("lightning")
    val lightning: String, // Keep as String, or use custom TypeAdapter for Boolean

    @SerializedName("open")
    val openTime: String? = null, // Optional, so nullable

    @SerializedName("close")
    val closeTime: String? = null, // Optional, so nullable

    @SerializedName("start")
    val startTime: String? = null, // Optional, for "Kekou" example

    @SerializedName("end")
    val endTime: String? = null, // Optional, for "Kekou" example

    @SerializedName("qtyLeft")
    val quantityLeft: String // Keep as String, or use custom TypeAdapter for Int
)
