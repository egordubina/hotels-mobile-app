package ru.egordubina.data.repositories

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import ru.egordubina.data.models.Hotel
import ru.egordubina.data.models.InfoAboutHotel
import ru.egordubina.data.models.asDomain
import ru.egordubina.domain.repositories.HotelRepository
import ru.egordubina.domain.models.Hotel as DomainHotel
import ru.egordubina.domain.models.InfoAboutHotel as DomainInfoAboutHotel

private const val API_URL_HOTEL = "https://run.mocky.io/v3/d144777c-a67f-4e35-867a-cacc3b827473"

class HotelRepositoryImpl : HotelRepository {
    private val client = HttpClient(CIO)

    override suspend fun loadHotel(): DomainHotel {
        val response = client.use {
            it.get(API_URL_HOTEL).bodyAsText()
        }
        val result = Json.decodeFromString<Hotel>(response)
        return result.asDomain()
    }
}

fun InfoAboutHotel.asDomain(): DomainInfoAboutHotel =
    DomainInfoAboutHotel(
        description = this.description,
        peculiarities = this.peculiarities
    )