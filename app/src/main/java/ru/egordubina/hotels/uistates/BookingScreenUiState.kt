package ru.egordubina.hotels.uistates

import ru.egordubina.hotels.models.TouristUi

sealed class BookingScreenUiState {
    data object Loading : BookingScreenUiState()
    data object LoadingPay : BookingScreenUiState()
    data object Error : BookingScreenUiState()
    data object UnsuccessfulPay : BookingScreenUiState()
    data class Content(
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
        val touristsList: List<TouristUi>
    ) : BookingScreenUiState()

    data class SuccessfulPay(
        val bookingNumber: Int
    ) : BookingScreenUiState()
}