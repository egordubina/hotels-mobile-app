package ru.egordubina.domain.usecases

import ru.egordubina.domain.models.BookingInfo
import ru.egordubina.domain.repositories.BookingRepository


interface LoadBookingInfoUseCase {
    suspend fun loadBookingInfo(): BookingInfo
}

class LoadBookingInfoUseCaseImpl(
    private val bookingRepository: BookingRepository
) : LoadBookingInfoUseCase {
    override suspend fun loadBookingInfo(): BookingInfo = bookingRepository.loadBookingInfo()
}
