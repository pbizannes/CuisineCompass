
import au.com.pbizannes.cuisinecompass.data.repository.DefaultRestaurantRepository
import au.com.pbizannes.cuisinecompass.data.source.remote.RestaurantService
import au.com.pbizannes.cuisinecompass.domain.models.Restaurant
import au.com.pbizannes.cuisinecompass.domain.models.RestaurantsResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class DefaultRestaurantRepositoryTest {

    private lateinit var mockRestaurantService: RestaurantService
    private lateinit var repository: DefaultRestaurantRepository

    private val sampleRestaurant1 = Restaurant(objectId = "1", name = "Pizza Delicious", address1 = "123 Main", suburb = "City", cuisines = listOf("Italian"), imageLink = null, openTime = "10am", closeTime = "10pm", deals = emptyList())
    private val sampleRestaurant2 = Restaurant(objectId = "2", name = "Sushi World", address1 = "456 Oak", suburb = "Town", cuisines = listOf("Japanese"), imageLink = null, openTime = "11am", closeTime = "9pm", deals = emptyList())
    private val sampleRestaurantsList = listOf(sampleRestaurant1, sampleRestaurant2)
    private val sampleRestaurantsResponse = RestaurantsResponse(restaurants = sampleRestaurantsList)

    @Before
    fun setUp() {
        mockRestaurantService = mockk()
        repository = DefaultRestaurantRepository(mockRestaurantService)
    }

    @Test
    fun `getRestaurants success - service returns successful response with data`() = runTest {
        val mockSuccessResponse = Response.success(sampleRestaurantsResponse)
        coEvery { mockRestaurantService.getRestaurants() } returns mockSuccessResponse

        val result = repository.getRestaurants()

        assertTrue("Result should be success", result.isSuccess)
        assertFalse("Result should not be failure", result.isFailure)
        val restaurants = result.getOrNull()
        assertNotNull("Restaurants list should not be null", restaurants)
        assertEquals("Restaurants list size should match sample", sampleRestaurantsList.size, restaurants?.size)
        assertEquals("First restaurant name should match", sampleRestaurant1.name, restaurants?.get(0)?.name)
    }

    @Test
    fun `getRestaurants success - service returns successful response but body is null`() = runTest {
        val mockSuccessResponseWithNullBody: Response<RestaurantsResponse> = Response.success(null)
        coEvery { mockRestaurantService.getRestaurants() } returns mockSuccessResponseWithNullBody

        val result = repository.getRestaurants()

        assertTrue("Result should be failure when body is null", result.isFailure)
        assertFalse("Result should not be success", result.isSuccess)
        val exception = result.exceptionOrNull()
        assertNotNull("Exception should not be null", exception)
        assertTrue("Exception should be NoSuchElementException", exception is NoSuchElementException)
    }

    @Test
    fun `getRestaurants failure - service call throws an exception`() = runTest {
        val expectedException = RuntimeException("Network connection failed")
        coEvery { mockRestaurantService.getRestaurants() } throws expectedException

        val result = repository.getRestaurants()

        assertTrue("Result should be failure when service throws exception", result.isFailure)
        assertFalse("Result should not be success", result.isSuccess)
        val exception = result.exceptionOrNull()
        assertNotNull("Exception should not be null", exception)
        assertTrue("Exception should be NoSuchElementException as per repository logic", exception is NoSuchElementException)
    }
}
