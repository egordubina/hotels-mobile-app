package ru.egordubina.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.egordubina.domain.repositories.HotelRepository
import ru.egordubina.domain.usecases.LoadHotelUseCase
import ru.egordubina.domain.usecases.LoadHotelUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    fun loadHotelUseCaseProvides(hotelRepository: HotelRepository): LoadHotelUseCase =
        LoadHotelUseCaseImpl(hotelRepository = hotelRepository)
}