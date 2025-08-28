package au.com.pbizannes.cuisinecompass.presentation.restaurant_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import au.com.pbizannes.cuisinecompass.domain.models.Restaurant
import au.com.pbizannes.cuisinecompass.domain.use_case.GetRestaurantsUseCase
import au.com.pbizannes.cuisinecompass.presentation.RestaurantViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RestaurantViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher() // Or StandardTestDispatcher()

    private lateinit var getRestaurantsUseCase: GetRestaurantsUseCase
    private lateinit var viewModel: RestaurantViewModel

    // Sample data for testing
    private val sampleDomainRestaurants = listOf(
        Restaurant(objectId = "1", name = "Pizza Place", address1 = "1 Main St", suburb = "City", cuisines = listOf("Italian", "Pizza"), imageLink = null, openTime = "", closeTime = "", deals = emptyList()),
        Restaurant(objectId = "2", name = "Sushi Spot", address1 = "2 Oak Ave", suburb = "Town", cuisines = listOf("Japanese", "Sushi"), imageLink = null, openTime = "", closeTime = "", deals = emptyList()),
        Restaurant(objectId = "3", name = "Burger Joint", address1 = "3 Pine Ln", suburb = "City", cuisines = listOf("American", "Burger"), imageLink = null, openTime = "", closeTime = "", deals = emptyList()),
        Restaurant(objectId = "4", name = "Pasta Bar", address1 = "4 Elm Rd", suburb = "Metro", cuisines = listOf("Italian", "Pasta"), imageLink = null, openTime = "", closeTime = "", deals = emptyList())
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getRestaurantsUseCase = mockk()
        coEvery { getRestaurantsUseCase.invoke() } returns Result.success(sampleDomainRestaurants)
        viewModel = RestaurantViewModel(getRestaurantsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is correct`() = runTest {
        val initialState = viewModel.uiState.first()

        assertTrue("Initial state should be loading", initialState.isLoading)
        assertTrue("Initial restaurants list should be empty", initialState.restaurants.isEmpty())
        assertEquals("Initial search query should be empty", "", initialState.searchQuery)
        assertNull("Initial error should be null", initialState.error)
    }

    @Test
    fun `loadRestaurants success - updates uiState with restaurants and not loading`() = runTest {
        coEvery { getRestaurantsUseCase.invoke() } returns Result.success(sampleDomainRestaurants)

        viewModel.loadRestaurants()

        val finalState = viewModel.uiState.value

        assertFalse("Should not be loading after success", finalState.isLoading)
        assertEquals("Restaurants list should match sample data", sampleDomainRestaurants.size, finalState.restaurants.size)
        assertEquals("First restaurant name should match", sampleDomainRestaurants[0].name, finalState.restaurants[0].name)
        assertNull("Error should be null on success", finalState.error)
        coVerify(exactly = 1) { getRestaurantsUseCase.invoke() } // Verify use case was called
    }

    @Test
    fun `loadRestaurants failure - updates uiState with error and not loading`() = runTest {
        val errorMessage = "Network Error"
        coEvery { getRestaurantsUseCase.invoke() } returns Result.failure(Exception(errorMessage))

        viewModel.loadRestaurants()

        val finalState = viewModel.uiState.value

        assertFalse("Should not be loading after error", finalState.isLoading)
        assertTrue("Restaurants list should be empty on error", finalState.restaurants.isEmpty())
        assertNotNull("Error should be present", finalState.error)
        assertEquals("Error message should match", "Restaurants are closed!", finalState.error) // As per ViewModel logic
        coVerify(exactly = 1) { getRestaurantsUseCase.invoke() }
    }

    @Test
    fun `onSearchQueryChanged - updates searchQuery and filters restaurants by name`() = runTest {
        coEvery { getRestaurantsUseCase.invoke() } returns Result.success(sampleDomainRestaurants)
        viewModel.loadRestaurants()
        advanceUntilIdle()

        val searchQuery = "Pizza"
        viewModel.onSearchQueryChanged(searchQuery)

        val finalState = viewModel.uiState.value
        assertEquals("Search query should be updated", searchQuery, finalState.searchQuery)
        assertEquals("Filtered list should contain 1 restaurant", 1, finalState.restaurants.size)
        assertEquals("Filtered restaurant should be 'Pizza Place'", "Pizza Place", finalState.restaurants[0].name)
    }

    @Test
    fun `onSearchQueryChanged - updates searchQuery and filters restaurants by cuisine`() = runTest {
        coEvery { getRestaurantsUseCase.invoke() } returns Result.success(sampleDomainRestaurants)
        viewModel.loadRestaurants()
        advanceUntilIdle()

        val searchQuery = "Sushi"
        viewModel.onSearchQueryChanged(searchQuery)

        val finalState = viewModel.uiState.value
        assertEquals("Search query should be updated", searchQuery, finalState.searchQuery)
        assertEquals("Filtered list should contain 1 restaurant", 1, finalState.restaurants.size)
        assertEquals("Filtered restaurant should be 'Sushi Spot'", "Sushi Spot", finalState.restaurants[0].name)
    }

    @Test
    fun `onSearchQueryChanged - empty query shows all restaurants`() = runTest {
        coEvery { getRestaurantsUseCase.invoke() } returns Result.success(sampleDomainRestaurants)
        viewModel.loadRestaurants()
        advanceUntilIdle()

        viewModel.onSearchQueryChanged("Pizza")
        val intermediateState = viewModel.uiState.value
        assertEquals("Should have 1 restaurant after 'Pizza' search", 1, intermediateState.restaurants.size)

        viewModel.onSearchQueryChanged("")

        val finalState = viewModel.uiState.value
        assertEquals("Search query should be empty", "", finalState.searchQuery)
        assertEquals("Should show all restaurants when query is empty", sampleDomainRestaurants.size, finalState.restaurants.size)
    }

    @Test
    fun `onSearchQueryChanged - no match returns empty list`() = runTest {
        coEvery { getRestaurantsUseCase.invoke() } returns Result.success(sampleDomainRestaurants)
        advanceUntilIdle()

        val searchQuery = "NonExistentFood"
        viewModel.onSearchQueryChanged(searchQuery)

        val finalState = viewModel.uiState.value
        assertEquals("Search query should be updated", searchQuery, finalState.searchQuery)
        assertTrue("Restaurant list should be empty for no match", finalState.restaurants.isEmpty())
    }
}
