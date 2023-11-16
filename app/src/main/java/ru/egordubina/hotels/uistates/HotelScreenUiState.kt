package ru.egordubina.hotels.uistates

sealed class HotelScreenUiState {
    data object Loading : HotelScreenUiState()
    data object Error : HotelScreenUiState()
    data class Content(
        val name: String,
        val address: String,
        val price: Int,
        val priceLabel: String,
        val rating: Byte,
        val ratingName: String,
        val hotelDescription: String,
        val peculiarities: List<String>
    ) : HotelScreenUiState()
}