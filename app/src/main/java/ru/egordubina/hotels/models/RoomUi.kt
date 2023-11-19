package ru.egordubina.hotels.models

import ru.egordubina.domain.models.RoomsDomain as RoomDomain

data class RoomUi(
    val id: Int,
    val name: String,
    val price: Int,
    val pricePer: String,
    val peculiarities: List<String>,
    val imagesUrls: List<String>,
)

fun RoomDomain.asUi(): RoomUi = RoomUi(
    id = this.id,
    name = this.name,
    price = this.price,
    pricePer = this.pricePer.lowercase(),
    peculiarities = this.peculiarities,
    imagesUrls = this.imagesUrls
)

fun List<RoomDomain>.asUi(): List<RoomUi> = map { it.asUi() }