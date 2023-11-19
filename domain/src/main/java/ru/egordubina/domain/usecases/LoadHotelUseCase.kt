package ru.egordubina.domain.usecases

import ru.egordubina.domain.models.HotelDomain
import ru.egordubina.domain.repositories.HotelRepository

interface LoadHotelUseCase {
    suspend fun loadHotel(): HotelDomain
}

class LoadHotelUseCaseImpl(
    private val hotelRepository: HotelRepository
) : LoadHotelUseCase {
    override suspend fun loadHotel(): HotelDomain = hotelRepository.loadHotel()
}