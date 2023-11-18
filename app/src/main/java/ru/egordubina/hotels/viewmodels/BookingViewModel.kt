package ru.egordubina.hotels.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.egordubina.domain.usecases.LoadBookingInfoUseCase
import ru.egordubina.hotels.uistates.BookingScreenUiState
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val loadBookingInfoUseCase: LoadBookingInfoUseCase
): ViewModel() {
    private var _uiState: MutableStateFlow<BookingScreenUiState> = MutableStateFlow(BookingScreenUiState.Loading)
    val uiState: StateFlow<BookingScreenUiState> = _uiState.asStateFlow()
    private var job: Job? = null

    init {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = BookingScreenUiState.Loading
            try {
                val result = loadBookingInfoUseCase.loadBookingInfo()
                _uiState.value = BookingScreenUiState.Content(
                    hotelName = result.hotelName,
                    hotelAddress = result.hotelAddress,
                    hotelRating = result.hotelRating,
                    ratingName = result.ratingName,
                    departure = result.departure,
                    arrivalCountry = result.arrivalCountry,
                    tourDateStart = result.tourDateStart,
                    tourDateEnd = result.tourDateEnd,
                    countOfNight = result.countOfNight,
                    roomName = result.roomName,
                    nutrition = result.nutrition,
                    tourPrice = result.tourPrice,
                    fuelCharge = result.fuelCharge,
                    serviceCharge = result.serviceCharge,
                )
            } catch (e: Exception) {
                _uiState.value = BookingScreenUiState.Error
            }
        }
    }
}