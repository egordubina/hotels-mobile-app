package ru.egordubina.domain.usecases

import ru.egordubina.domain.models.BookingInfoDomain
import ru.egordubina.domain.repositories.BookingRepository


interface LoadBookingInfoUseCase {
    suspend fun loadBookingInfo(): BookingInfoDomain
}

class LoadBookingInfoUseCaseImpl(
    private val bookingRepository: BookingRepository
) : LoadBookingInfoUseCase {
    override suspend fun loadBookingInfo(): BookingInfoDomain = bookingRepository.loadBookingInfo()
}
