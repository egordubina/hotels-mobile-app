package ru.egordubina.data.repositories

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import ru.egordubina.data.models.Hotel
import ru.egordubina.data.models.InfoAboutHotel
import ru.egordubina.data.models.asDomain
import ru.egordubina.domain.repositories.HotelRepository
import javax.inject.Inject
import ru.egordubina.domain.models.Hotel as DomainHotel
import ru.egordubina.domain.models.InfoAboutHotel as DomainInfoAboutHotel

private const val API_URL_HOTEL = "https://run.mocky.io/v3/d144777c-a67f-4e35-867a-cacc3b827473"

class HotelRepositoryImpl @Inject constructor(
    private val client: HttpClient
) : HotelRepository {
    override suspend fun loadHotel(): DomainHotel {
        val response = client.use {
            it.get(API_URL_HOTEL).bodyAsText()
        }
        return Json.decodeFromString<Hotel>(response).asDomain()
    }
}

fun InfoAboutHotel.asDomain(): DomainInfoAboutHotel =
    DomainInfoAboutHotel(
        description = this.description,
        peculiarities = this.peculiarities
    )