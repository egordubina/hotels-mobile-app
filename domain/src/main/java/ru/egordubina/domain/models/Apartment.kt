package ru.egordubina.domain.models

data class Apartment(
    val id: Int,
    val name: String,
    val price: Int,
    val pricePer: String,
    val peculiarities: List<String>,
    val imagesUrls: List<String>,
)