package ru.egordubina.domain.repositories

import ru.egordubina.domain.models.Apartment

interface ApartmentsRepository {
    suspend fun loadApartments(): List<Apartment>
}