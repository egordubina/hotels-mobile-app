package ru.egordubina.domain.usecases

import ru.egordubina.domain.models.Apartment
import ru.egordubina.domain.repositories.ApartmentsRepository

interface LoadApartmentsUseCase {
    suspend fun loadApartments(): List<Apartment>
}

class LoadApartmentsUseCaseImpl(private val apartmentsRepository: ApartmentsRepository) : LoadApartmentsUseCase {
    override suspend fun loadApartments(): List<Apartment> = apartmentsRepository.loadApartments()
}