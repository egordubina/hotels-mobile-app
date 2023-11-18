package ru.egordubina.hotels.uistates

sealed class BookingScreenUiState {
    data object Loading : BookingScreenUiState()
    data object Error : BookingScreenUiState()
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
        val tourPrice: Int,
        val fuelCharge: Int,
        val serviceCharge: Int,
    ) : BookingScreenUiState()
}