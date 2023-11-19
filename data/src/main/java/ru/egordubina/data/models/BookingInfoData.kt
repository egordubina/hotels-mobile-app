package ru.egordubina.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.egordubina.domain.models.BookingInfoDomain as BookingInfoDomain

@Serializable
data class BookingInfoData(
    @SerialName("id") val id: Int,
    @SerialName("hotel_name") val hotelName: String,
    @SerialName("hotel_adress") val hotelAddress: String,
    @SerialName("horating") val hotelRating: Int,
    @SerialName("rating_name") val ratingName: String,
    @SerialName("departure") val departure: String,
    @SerialName("arrival_country") val arrivalCountry: String,
    @SerialName("tour_date_start") val tourDateStart: String,
    @SerialName("tour_date_stop") val tourDateEnd: String,
    @SerialName("number_of_nights") val countOfNight: Int,
    @SerialName("room") val roomName: String,
    @SerialName("nutrition") val nutrition: String,
    @SerialName("tour_price") val tourPrice: Int,
    @SerialName("fuel_charge") val fuelCharge: Int,
    @SerialName("service_charge") val serviceCharge: Int,
)

fun BookingInfoData.asDomain(): BookingInfoDomain = BookingInfoDomain(
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
    tourPrice = this.tourPrice,
    fuelCharge = this.fuelCharge,
    serviceCharge = this.serviceCharge,
)