package au.com.pbizannes.cuisinecompass.di

import au.com.pbizannes.cuisinecompass.data.repository.DefaultRestaurantRepository
import au.com.pbizannes.cuisinecompass.data.source.remote.RestaurantService
import au.com.pbizannes.cuisinecompass.domain.repository.RestaurantRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRestaurantRepository(service: RestaurantService): RestaurantRepository =
        DefaultRestaurantRepository(service)
}