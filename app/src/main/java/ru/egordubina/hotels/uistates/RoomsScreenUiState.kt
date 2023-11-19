package ru.egordubina.hotels.uistates

import ru.egordubina.hotels.models.RoomUi as RoomUi


sealed class RoomsScreenUiState {
    data object Error : RoomsScreenUiState()
    data object Loading : RoomsScreenUiState()
    data class Content(
        val apartments: List<RoomUi>
    ): RoomsScreenUiState()
}