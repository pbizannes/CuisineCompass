package au.com.pbizannes.cuisinecompass.presentation.restaurant_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.com.pbizannes.cuisinecompass.domain.models.Restaurant
import au.com.pbizannes.cuisinecompass.domain.use_case.GetRestaurantsUseCase
import au.com.pbizannes.cuisinecompass.presentation.models.sampleRestaurants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RestaurantListUiState(
    val searchQuery: String = "",
    val restaurants: List<Restaurant> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class RestaurantListViewModel @Inject constructor(
    private val getRestaurantsUseCase: GetRestaurantsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(RestaurantListUiState())
    val uiState: StateFlow<RestaurantListUiState> = _uiState.asStateFlow()

    // Keep a reference to all loaded restaurants for filtering
    private var allRestaurants: List<Restaurant> =
        sampleRestaurants

    init {
        loadRestaurants()
    }

    // In a real app, this function would fetch data from a repository (network/database)
    fun loadRestaurants() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            // Simulate network delay or data fetching
            // kotlinx.coroutines.delay(1000)
            val restaurantResponse = getRestaurantsUseCase.invoke()
            if (restaurantResponse.isSuccess) {
                allRestaurants = restaurantResponse.getOrNull() ?: listOf()
                _uiState.update {
                    it.copy(
                        restaurants = filterRestaurants(allRestaurants, it.searchQuery),
                        isLoading = false
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        restaurants = listOf(),
                        error = "Restaurants are closed!",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        // In a real app, you might want to debounce this operation to avoid excessive filtering
        // while the user is typing.
        // viewModelScope.launch {
        //     delay(300) // example debounce time
        //     _uiState.update {
        //         it.copy(restaurants = filterRestaurants(allRestaurants, query))
        //     }
        // }
        // For simplicity in this example, direct update:
        _uiState.update {
            it.copy(restaurants = filterRestaurants(allRestaurants, query))
        }
    }

    private fun filterRestaurants(
        restaurants: List<Restaurant>,
        query: String
    ): List<Restaurant> {
        if (query.isBlank()) {
            return restaurants // Return all if query is blank
        }
        // Filter by restaurant name or cuisine type containing the query
        return restaurants.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.cuisines.any { cuisine -> cuisine.contains(query, ignoreCase = true) }
        }
    }
}
