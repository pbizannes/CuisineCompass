package au.com.pbizannes.cuisinecompass.di

import au.com.pbizannes.cuisinecompass.data.source.remote.RestaurantService
import au.com.pbizannes.cuisinecompass.domain.repository.RestaurantRepository
import com.xero.invoicetracker.invoiceviewer.data.source.DefaultInvoiceRepository
import com.xero.invoicetracker.invoiceviewer.data.source.network.InvoiceService
import com.xero.invoicetracker.invoiceviewer.domain.repository.InvoiceRepository
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