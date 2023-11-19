package ru.egordubina.domain.repositories

import ru.egordubina.domain.models.BookingInfoDomain

interface BookingRepository {
    suspend fun loadBookingInfo(): BookingInfoDomain
}