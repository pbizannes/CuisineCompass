package au.com.pbizannes.cuisinecompass.domain.repository

import au.com.pbizannes.cuisinecompass.domain.models.Restaurant

interface RestaurantRepository {
    /**
     * Get a List of [List<NewsSource>].
     */
    suspend fun getRestaurants(): Result<List<Restaurant>>
}
