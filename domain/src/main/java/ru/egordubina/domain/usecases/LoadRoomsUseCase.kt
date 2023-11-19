package ru.egordubina.domain.usecases

import ru.egordubina.domain.models.RoomsDomain
import ru.egordubina.domain.repositories.RoomsRepository

interface LoadApartmentsUseCase {
    suspend fun loadRooms(): List<RoomsDomain>
}

class LoadApartmentsUseCaseImpl(
    private val roomsRepository: RoomsRepository
) : LoadApartmentsUseCase {
    override suspend fun loadRooms(): List<RoomsDomain> = roomsRepository.loadRooms()
}