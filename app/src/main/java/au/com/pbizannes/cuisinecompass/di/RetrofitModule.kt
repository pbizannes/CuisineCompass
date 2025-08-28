package au.com.pbizannes.cuisinecompass.di

import au.com.pbizannes.cuisinecompass.BuildConfig
import au.com.pbizannes.cuisinecompass.data.source.remote.RestaurantService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    fun provideBaseUrl(): String = BuildConfig.RESTAURANT_URL

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRestaurantService(retrofit: Retrofit): RestaurantService = retrofit.create(RestaurantService::class.java)
}