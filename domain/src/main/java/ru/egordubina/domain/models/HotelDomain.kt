package ru.egordubina.domain.models

data class HotelDomain(
    val id: Int,
    val name: String,
    val address: String,
    val minimalPrice: Int,
    val priceForIt: String,
    val rating: Int,
    val ratingName: String,
    val imagesUrls: List<String>,
    val aboutHotel: InfoAboutHotelDomain,
)