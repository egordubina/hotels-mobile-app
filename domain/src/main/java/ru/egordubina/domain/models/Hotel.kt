package ru.egordubina.domain.models

data class Hotel(
    val id: Int,
    val name: String,
    val address: String,
    val minimalPrice: Int,
    val priceForIt: String,
    val rating: Byte,
    val ratingName: String,
    val imagesUrls: List<String>,
    val aboutHotel: Pair<String, List<String>>,
)