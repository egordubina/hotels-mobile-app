package ru.egordubina.hotels.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.egordubina.domain.usecases.LoadBookingInfoUseCase
import ru.egordubina.hotels.models.asUi
import ru.egordubina.hotels.uistates.BookingScreenUiState
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val loadBookingInfoUseCase: LoadBookingInfoUseCase
) : ViewModel(), IViewModel<BookingScreenUiState> {
    private var _uiState: MutableStateFlow<BookingScreenUiState> =
        MutableStateFlow(BookingScreenUiState.Loading)
    override val uiState: StateFlow<BookingScreenUiState> = _uiState.asStateFlow()
    private val _prevUiState: MutableStateFlow<BookingScreenUiState> =
        MutableStateFlow(BookingScreenUiState.Loading)
    private var job: Job? = null

    init {
        loadData()
    }

    override fun loadData() {
        _uiState.update { BookingScreenUiState.Loading }
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = loadBookingInfoUseCase.loadBookingInfo().asUi()
                _uiState.update {
                    BookingScreenUiState.Content(
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
                        totalPrice = result.totalPrice,
                    )
                }
                _prevUiState.update { _uiState.value }
            } catch (e: Exception) {
                _uiState.update { BookingScreenUiState.Error }
            }
        }
    }

    fun bookingPay() {
        _uiState.update { BookingScreenUiState.LoadingPay }
        job?.cancel()
        job = viewModelScope.launch {
            try {
                val randomBookingNumber = Random.nextInt(100_000, 999_999)
                _uiState.update {
                    BookingScreenUiState.SuccessfulPay(bookingNumber = randomBookingNumber)
                }
            } catch (e: Exception) {
                _uiState.update { BookingScreenUiState.UnsuccessfulPay }
            }
        }
    }
}