package ru.egordubina.hotels.models

data class TouristUi(
    val id: Int,
    val isVisible: Boolean = false,
    val name: String = "",
    val surname: String = "",
    val birthday: String = "",
    val country: String = "",
    val pasNumber: String = "",
    val dataPas: String = "",
)
