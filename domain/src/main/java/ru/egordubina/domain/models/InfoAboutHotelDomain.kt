package ru.egordubina.domain.models

data class InfoAboutHotelDomain(
    val description: String,
    val peculiarities: List<String>,
)