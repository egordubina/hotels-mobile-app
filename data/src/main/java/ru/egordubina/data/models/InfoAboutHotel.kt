package ru.egordubina.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InfoAboutHotel(
    @SerialName("description") val description: String,
    @SerialName("peculiarities") val peculiarities: List<String>,
)