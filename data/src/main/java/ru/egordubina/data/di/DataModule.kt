package ru.egordubina.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import ru.egordubina.data.repositories.ApartmentsRepositoryImpl
import ru.egordubina.data.repositories.BookingRepositoryImpl
import ru.egordubina.data.repositories.HotelRepositoryImpl
import ru.egordubina.domain.repositories.ApartmentsRepository
import ru.egordubina.domain.repositories.BookingRepository
import ru.egordubina.domain.repositories.HotelRepository

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun httpClientProvide(): HttpClient = HttpClient(CIO)

    @Provides
    fun hotelRepositoryProvide(client: HttpClient): HotelRepository = HotelRepositoryImpl(client = client)

    @Provides
    fun apartmentsRepositoryProvide(client: HttpClient): ApartmentsRepository =
        ApartmentsRepositoryImpl(client = client)

    @Provides
    fun bookingRepositoryProvide(client: HttpClient): BookingRepository =
        BookingRepositoryImpl(client = client)
}