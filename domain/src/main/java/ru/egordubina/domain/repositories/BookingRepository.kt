package ru.egordubina.domain.repositories

import ru.egordubina.domain.models.BookingInfo

interface BookingRepository {
    suspend fun loadBookingInfo(): BookingInfo
}