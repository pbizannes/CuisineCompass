package au.com.pbizannes.cuisinecompass.presentation.models

import android.os.Parcelable
import au.com.pbizannes.cuisinecompass.domain.models.Deal
import au.com.pbizannes.cuisinecompass.domain.models.Restaurant
import kotlinx.parcelize.Parcelize
import okhttp3.Address

@Parcelize
data class DisplayableRestaurant(
    val id: String,
    val name: String,
    val imageUrl: String,
    val openTime: String,
    val closeTime: String,
    val deal: RestaurantDeal? = null,
    val deals: List<RestaurantDeal> = listOf(),
    val address: String,
    val distanceInfo: String, // e.g., "0.5km Away, Lower East"
    val cuisines: List<String>,
    val services: List<String>, // e.g., ["Dine In", "Takeaway", "Order Online"]
    val isFavourite: Boolean = false
) : Parcelable

// Sample data for preview and development
val sampleRestaurantCards = listOf(
    DisplayableRestaurant(
        id = "1",
        name = "Ferdinand",
        imageUrl = "https://images.unsplash.com/photo-1513104890138-7c749659a591?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80", // Replace with actual placeholder or real URL
        deal = RestaurantDeal("1", conditionText = "Anytime today", discountText = "50% off", dealsLeftText = "3 Deals Left"),
        distanceInfo = "0.5km Away, Lower East",
        cuisines = listOf("Italian", "Pizza"),
        services = listOf("Dine In", "Takeaway", "Order Online"),
        openTime = "9am",
        closeTime = "8pm",
        address = "98/100 High St, Northcote",
        isFavourite = false,
    ),
    DisplayableRestaurant(
        id = "2",
        name = "The Meatball & Wine Co",
        imageUrl = "https://images.unsplash.com/photo-1555939594-58d7cb561ad1?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=687&q=80", // Replace
        deal = RestaurantDeal("2", conditionText = "Arrive before 7:00pm", discountText = "40% off", dealsLeftText = "5 Deals Left"),
        distanceInfo = "0.5km Away, Lower East",
        cuisines = listOf("Australian"),
        services = listOf("Dine In", "Order Online"),
        openTime = "8am",
        closeTime = "9pm",
        address = "105 Swan Street Richmond",
        isFavourite = true
    ),
    DisplayableRestaurant(
        id = "3",
        name = "Another Place",
        imageUrl = "https://images.unsplash.com/photo-1540189549336-e6e99c3679fe?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=687&q=80", // Replace
        deal = RestaurantDeal("3", conditionText = "Arrive after 11:00am", discountText = "20% off", dealsLeftText = "2 Deals Left"),
        distanceInfo = "1.2km Away, CBD",
        cuisines = listOf("Cafe", "Modern"),
        services = listOf("Dine In", "Takeaway"),
        openTime = "6am",
        closeTime = "6pm",
        address = "380 Collins St, Melbourne",
        isFavourite = false
    )
)

val sampleRestaurants = listOf(
    Restaurant(
        objectId = "1",
        name = "Ferdinand",
        imageLink = "https://images.unsplash.com/photo-1513104890138-7c749659a591?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80", // Replace with actual placeholder or real URL
        deals = listOf(Deal("1", "30% off", "Anytime today",  "Arrive before 7:00pm", quantityLeft = "5")),

        address1 = "0.5km Away",
        suburb = "Lower East",
        cuisines = listOf("Italian", "Pizza"),
        openTime = "9am",
        closeTime = "8pm"
    ),
    Restaurant(
        objectId = "2",
        name = "Meatball & Wine Co",
        imageLink = "https://images.unsplash.com/photo-1555939594-58d7cb561ad1?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=687&q=80", // Replace
        deals = listOf(Deal("1", "40% off", "Dine In", "Arrive before 7:00pm", quantityLeft = "5")),
        address1 = "0.5km Away",
        suburb = "Lower East",
        cuisines = listOf("Australian"),
        openTime = "9am",
        closeTime = "8pm"
    ),
)
