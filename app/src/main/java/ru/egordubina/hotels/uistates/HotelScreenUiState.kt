package ru.egordubina.hotels.uistates

sealed class HotelScreenUiState {
    data object Loading : HotelScreenUiState()
    data object Error : HotelScreenUiState()
    data class Content(
        val name: String,
        val address: String,
        val price: Int,
        val priceLabel: String,
        val rating: Int,
        val ratingName: String,
        val hotelDescription: String,
        val peculiarities: List<String>,
        val imagesUrls: List<String>
    ) : HotelScreenUiState()
}