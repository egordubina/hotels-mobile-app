package ru.egordubina.hotels.uistates

sealed class BookingScreenUiState {
    data object Loading : BookingScreenUiState()
    data object LoadingPay : BookingScreenUiState()
    data object Error : BookingScreenUiState()
    data object UnsuccessfulPay : BookingScreenUiState()

    data object Reload : BookingScreenUiState()

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
    ) : BookingScreenUiState()

    data class SuccessfulPay(
        val bookingNumber: Int
    ) : BookingScreenUiState()
}