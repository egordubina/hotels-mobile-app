package ru.egordubina.domain.usecases

import ru.egordubina.domain.models.Hotel
import ru.egordubina.domain.repositories.HotelRepository

interface LoadHotelUseCase {
    suspend fun loadHotel(): Hotel
}

class LoadHotelUseCaseImpl(private val hotelRepository: HotelRepository) : LoadHotelUseCase {
    override suspend fun loadHotel(): Hotel = hotelRepository.loadHotel()
}