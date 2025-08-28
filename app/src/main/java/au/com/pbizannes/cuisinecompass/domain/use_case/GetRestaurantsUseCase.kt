package au.com.pbizannes.cuisinecompass.domain.use_case

import au.com.pbizannes.cuisinecompass.domain.models.Restaurant

interface GetRestaurantsUseCase {
    /**
     * gets a list of restaurants.
     *
     * @return A Result containing a list of [Restaurant] on success, or an error on failure.
     */
    suspend operator fun invoke(): Result<List<Restaurant>>
}
