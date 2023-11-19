package ru.egordubina.hotels.models

import ru.egordubina.domain.models.InfoAboutHotelDomain

data class InfoAboutHotelUi(
    val description: String,
    val peculiarities: List<String>,
)

fun InfoAboutHotelDomain.asUi() = InfoAboutHotelUi(
    description = this.description,
    peculiarities = this.peculiarities
)