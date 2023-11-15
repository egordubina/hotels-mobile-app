package ru.egordubina.hotels.uistates

sealed class HotelScreenUiState {
    data object Loading : HotelScreenUiState()
    data object Error : HotelScreenUiState()
    data class Content(
        val name: String,
    ) : HotelScreenUiState()
}