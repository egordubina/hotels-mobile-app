package ru.egordubina.hotels.models

import ru.egordubina.domain.models.BookingInfoDomain
import ru.egordubina.domain.utils.toRubInt

data class BookingInfoUi(
    val id: Int,
    val hotelName: String,
    val hotelAddress: String,
    val hotelRating: Int,
    val ratingName: String,
    val departure: String,
    val arrivalCountry: String,
    val tourDateStart: String,
    val tourDateEnd: String,
    val countOfNight: Int,
    val roomName: String,
    val nutrition: String,
    val tourPrice: String,
    val fuelCharge: String,
    val serviceCharge: String,
    val totalPrice: String,
)

fun BookingInfoDomain.asUi() = BookingInfoUi(
    id = this.id,
    hotelName = this.hotelName,
    hotelAddress = this.hotelAddress,
    hotelRating = this.hotelRating,
    ratingName = this.ratingName,
    departure = this.departure,
    arrivalCountry = this.arrivalCountry,
    tourDateStart = this.tourDateStart,
    tourDateEnd = this.tourDateEnd,
    countOfNight = this.countOfNight,
    roomName = this.roomName,
    nutrition = this.nutrition,
    tourPrice = this.tourPrice.toRubInt(),
    fuelCharge = this.fuelCharge.toRubInt(),
    serviceCharge = this.serviceCharge.toRubInt(),
    totalPrice = (this.tourPrice + this.fuelCharge + this.serviceCharge).toRubInt(),
)