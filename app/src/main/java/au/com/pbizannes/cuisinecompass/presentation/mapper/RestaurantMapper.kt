package au.com.pbizannes.cuisinecompass.presentation.mapper

import au.com.pbizannes.cuisinecompass.domain.models.Deal
import au.com.pbizannes.cuisinecompass.domain.models.Restaurant
import au.com.pbizannes.cuisinecompass.presentation.models.DisplayableRestaurant
import au.com.pbizannes.cuisinecompass.presentation.models.RestaurantDeal

object RestaurantMapper {
    private fun createCondition(deal: Deal): String =
        when {
            deal.openTime != null && deal.closeTime != null -> {
                "Between ${deal.openTime} to ${deal.closeTime}"
            }
            deal.lightning == "true" -> "Anytime today"
            deal.quantityLeft.toInt() > 0 -> "Only ${deal.quantityLeft} left"
            else -> ""
        }

    private fun createServices(deal: Deal?): List<String> {
        val conditions = mutableListOf<String>()
        if (deal?.dineIn == "true") {
            conditions.add("Dine In")
        }
        if (deal?.lightning == "true") {
            conditions.add("Today only")
        }
        return conditions
    }

    fun toPresentation(restaurant: Restaurant): DisplayableRestaurant {
        val restaurantDeals = restaurant.deals
            .sortedByDescending { deal ->
                deal.discount.toInt()
            }
            .map { deal ->
            RestaurantDeal(
                id = deal.objectId,
                discountText = "${deal.discount}% off",
                conditionText = createCondition(deal),
                dealsLeftText = "${deal.quantityLeft} Deals Left"
            )
        }

        // Calculate distance using google maps
        val distanceInfoText = "3km Away"

        val servicesPresentation: List<String> = createServices(deal = restaurant.deals.firstOrNull())

        return DisplayableRestaurant(
            id = restaurant.objectId,
            name = restaurant.name,
            imageUrl = restaurant.imageLink ?: "",
            deal = restaurantDeals.firstOrNull(),
            deals = restaurantDeals,
            distanceInfo = distanceInfoText,
            cuisines = restaurant.cuisines,
            services = servicesPresentation,
            openTime = restaurant.openTime,
            closeTime = restaurant.closeTime,
            address = "${restaurant.address1} ${restaurant.suburb}",
            isFavourite = false // use DataStore to store preferences here (no time to implement)
        )
    }

    fun toPresentationList(restaurants: List<Restaurant>): List<DisplayableRestaurant> {
        return restaurants.map { toPresentation(it) }
    }
}
