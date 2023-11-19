package ru.egordubina.hotels.models

import ru.egordubina.domain.models.HotelDomain

data class HotelUi(
    val id: Int,
    val name: String,
    val address: String,
    val minimalPrice: Int,
    val priceForIt: String,
    val rating: Int,
    val ratingName: String,
    val imagesUrls: List<String>,
    val aboutHotel: InfoAboutHotelUi,
)

fun HotelDomain.asUi(): HotelUi = HotelUi(
    id = this.id,
    name = this.name,
    address = this.address,
    minimalPrice = this.minimalPrice,
    priceForIt = this.priceForIt.lowercase(),
    rating = this.rating,
    ratingName = this.ratingName,
    imagesUrls = this.imagesUrls,
    aboutHotel = this.aboutHotel.asUi(),
)