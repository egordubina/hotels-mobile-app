package ru.egordubina.domain.repositories

import ru.egordubina.domain.models.RoomsDomain

interface RoomsRepository {
    suspend fun loadRooms(): List<RoomsDomain>
}