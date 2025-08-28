package au.com.pbizannes.cuisinecompass.data.repository

import au.com.pbizannes.cuisinecompass.data.source.remote.RestaurantService
import au.com.pbizannes.cuisinecompass.domain.models.Restaurant
import au.com.pbizannes.cuisinecompass.domain.repository.RestaurantRepository

class DefaultRestaurantRepository(val restaurantService: RestaurantService): RestaurantRepository {
    override suspend fun getRestaurants(): Result<List<Restaurant>> {
        return try {
            val response = restaurantService.getRestaurants()

            val responseBody = response.body()
            if (response.isSuccessful) {
                if (responseBody != null) {
                    Result.success(responseBody.restaurants)
                } else {
                    Result.failure(NoSuchElementException())
                }
            } else {
                Result.failure(NoSuchElementException())
            }
        } catch (error: Exception) {
            Result.failure(NoSuchElementException())
        }
    }
}