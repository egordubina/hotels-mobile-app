package ru.egordubina.domain.repositories

import ru.egordubina.domain.models.Hotel

interface HotelRepository {
    suspend fun loadHotel(): Hotel
}
