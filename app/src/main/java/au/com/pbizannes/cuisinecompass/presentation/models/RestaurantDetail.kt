package au.com.pbizannes.cuisinecompass.presentation.models

import androidx.compose.ui.graphics.vector.ImageVector

data class RestaurantImage(
    val id: String,
    val url: String
)

data class ActionItem(
    val id: String,
    val label: String,
    val icon: ImageVector
)

data class DealDetail(
    val id: String,
    val discountText: String, // e.g., "30% Off"
    val conditionText: String, // e.g., "Between 12:00PM - 3:00PM"
    val dealsLeftText: String // e.g., "5 Deals Left"
)

data class RestaurantDetail(
    val id: String,
    val name: String,
    val images: List<RestaurantImage>,
    val isNew: Boolean = false,
    val actionItems: List<ActionItem>,
    val tags: List<String>, // e.g., ["Pizza", "Casual Dining", "Vegetarian", "$"]
    val hours: String, // e.g., "12:00PM - 11:00PM"
    val address: String, // e.g., "105 Swan Street Richmond"
    val distance: String, // e.g., "1.0km Away"
    val deals: List<RestaurantDeal>,
    val isFavorite: Boolean = false
)
