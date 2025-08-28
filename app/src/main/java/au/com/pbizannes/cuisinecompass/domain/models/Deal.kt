package au.com.pbizannes.cuisinecompass.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Deal(
    @SerializedName("objectId")
    val objectId: String,

    @SerializedName("discount")
    val discount: String,

    @SerializedName("dineIn")
    val dineIn: String,

    @SerializedName("lightning")
    val lightning: String,

    @SerializedName("open")
    val openTime: String? = null,

    @SerializedName("close")
    val closeTime: String? = null,

    @SerializedName("start")
    val startTime: String? = null,

    @SerializedName("end")
    val endTime: String? = null,

    @SerializedName("qtyLeft")
    val quantityLeft: String
) : Parcelable
