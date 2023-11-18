package ru.egordubina.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.egordubina.domain.repositories.ApartmentsRepository
import ru.egordubina.domain.repositories.BookingRepository
import ru.egordubina.domain.repositories.HotelRepository
import ru.egordubina.domain.usecases.LoadApartmentsUseCase
import ru.egordubina.domain.usecases.LoadApartmentsUseCaseImpl
import ru.egordubina.domain.usecases.LoadBookingInfoUseCase
import ru.egordubina.domain.usecases.LoadBookingInfoUseCaseImpl
import ru.egordubina.domain.usecases.LoadHotelUseCase
import ru.egordubina.domain.usecases.LoadHotelUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    fun loadHotelUseCaseProvides(hotelRepository: HotelRepository): LoadHotelUseCase =
        LoadHotelUseCaseImpl(hotelRepository = hotelRepository)

    @Provides
    fun loadApartmentsUseCaseProvides(apartmentsRepository: ApartmentsRepository): LoadApartmentsUseCase =
        LoadApartmentsUseCaseImpl(apartmentsRepository = apartmentsRepository)

    @Provides
    fun loadBookingInfoUseCaseProvider(bookingRepository: BookingRepository): LoadBookingInfoUseCase =
        LoadBookingInfoUseCaseImpl(bookingRepository = bookingRepository)
}