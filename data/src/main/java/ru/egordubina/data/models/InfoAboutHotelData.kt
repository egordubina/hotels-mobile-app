package ru.egordubina.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.egordubina.domain.models.InfoAboutHotelDomain

@Serializable
data class InfoAboutHotelData(
    @SerialName("description") val description: String,
    @SerialName("peculiarities") val peculiarities: List<String>,
)

fun InfoAboutHotelData.asDomain(): InfoAboutHotelDomain =
    InfoAboutHotelDomain(
        description = this.description,
        peculiarities = this.peculiarities
    )