package ru.egordubina.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ApartmentsApiAnswer(
    @SerialName("rooms") val rooms: List<Apartment>
)