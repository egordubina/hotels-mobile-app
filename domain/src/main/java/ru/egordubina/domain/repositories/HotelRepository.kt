package ru.egordubina.domain.repositories

import ru.egordubina.domain.models.HotelDomain

interface HotelRepository {
    suspend fun loadHotel(): HotelDomain
}
