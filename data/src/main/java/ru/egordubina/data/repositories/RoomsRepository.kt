package ru.egordubina.data.repositories

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import ru.egordubina.data.models.RoomsApiAnswerData
import ru.egordubina.data.models.asDomain
import ru.egordubina.domain.models.RoomsDomain
import ru.egordubina.domain.repositories.RoomsRepository
import javax.inject.Inject

private const val API_URL_APARTMENTS = "https://run.mocky.io/v3/8b532701-709e-4194-a41c-1a903af00195"

class RoomsRepositoryImpl @Inject constructor(
    private val client: HttpClient
) : RoomsRepository {
    override suspend fun loadRooms(): List<RoomsDomain> {
        val response = client.get(API_URL_APARTMENTS).bodyAsText()
        return Json.decodeFromString<RoomsApiAnswerData>(response).rooms.asDomain()
    }
}