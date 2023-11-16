package ru.egordubina.hotels.uistates

import ru.egordubina.domain.models.Apartment

sealed class ApartmentsScreenUiState {
    data object Error : ApartmentsScreenUiState()
    data object Loading : ApartmentsScreenUiState()
    data class Content(
        val apartments: List<Apartment>
    ): ApartmentsScreenUiState()
}