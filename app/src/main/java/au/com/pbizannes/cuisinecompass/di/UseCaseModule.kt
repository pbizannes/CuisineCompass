package au.com.pbizannes.cuisinecompass.di

import au.com.pbizannes.cuisinecompass.domain.repository.RestaurantRepository
import au.com.pbizannes.cuisinecompass.domain.use_case.GetRestaurantsUseCase
import au.com.pbizannes.cuisinecompass.domain.use_case.GetRestaurantsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideGetRestaurantUseCase(restaurantRepository: RestaurantRepository, @DefaultDispatcher dispatcher: CoroutineDispatcher): GetRestaurantsUseCase =
        GetRestaurantsUseCaseImpl(restaurantRepository, dispatcher)
}