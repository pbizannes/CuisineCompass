package au.com.pbizannes.cuisinecompass.data.source.remote

import retrofit2.Response
import retrofit2.http.GET

interface RestaurantService {
    @GET("challengedata.json")
    suspend fun getRestaurants(): Response<RestaurantResponse>
}
