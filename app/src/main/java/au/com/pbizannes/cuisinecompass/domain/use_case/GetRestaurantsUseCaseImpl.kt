package au.com.pbizannes.cuisinecompass.domain.use_case

import au.com.pbizannes.cuisinecompass.di.DefaultDispatcher
import au.com.pbizannes.cuisinecompass.domain.models.Restaurant
import au.com.pbizannes.cuisinecompass.domain.repository.RestaurantRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of [GetRestaurantsUseCase].
 */
class GetRestaurantsUseCaseImpl @Inject constructor(
    private val restaurantRepository: RestaurantRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : GetRestaurantsUseCase {

    /**
     * gets a list of restaurants from the repository.
     */
    override suspend operator fun invoke(): Result<List<Restaurant>> {
        return withContext(dispatcher) {
            restaurantRepository.getRestaurants()
        }
    }
}
