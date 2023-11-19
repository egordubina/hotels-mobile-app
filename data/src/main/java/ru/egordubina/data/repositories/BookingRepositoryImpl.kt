package ru.egordubina.data.repositories

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import ru.egordubina.data.models.asDomain
import ru.egordubina.domain.repositories.BookingRepository
import javax.inject.Inject
import ru.egordubina.data.models.BookingInfoData as BookingInfoData
import ru.egordubina.domain.models.BookingInfoDomain as BookingInfoDomain

private const val API_URL_BOOKING = "https://run.mocky.io/v3/63866c74-d593-432c-af8e-f279d1a8d2ff"

class BookingRepositoryImpl @Inject constructor(
    private val client: HttpClient
) : BookingRepository {
    override suspend fun loadBookingInfo(): BookingInfoDomain {
        val response = client.use {
            it.get(API_URL_BOOKING).bodyAsText()
        }
        Log.d("API", response)
        return Json.decodeFromString<BookingInfoData>(response).asDomain()
    }
}