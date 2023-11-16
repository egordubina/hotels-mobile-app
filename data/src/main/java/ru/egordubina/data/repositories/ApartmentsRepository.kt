package ru.egordubina.data.repositories

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import ru.egordubina.data.models.Apartment
import ru.egordubina.data.models.ApartmentsApiAnswer
import ru.egordubina.data.models.asDomain
import ru.egordubina.domain.repositories.ApartmentsRepository
import ru.egordubina.domain.models.Apartment as DomainApartment

private const val API_URL_APARTMENTS = "https://run.mocky.io/v3/8b532701-709e-4194-a41c-1a903af00195"

class ApartmentsRepositoryImpl : ApartmentsRepository {
    private val client = HttpClient(CIO)
    override suspend fun loadApartments(): List<DomainApartment> {
        val response = client.use {
            it.get(API_URL_APARTMENTS).bodyAsText()
        }
        val result = Json.decodeFromString<ApartmentsApiAnswer>(response)
        return result.rooms.asDomain()
    }
}