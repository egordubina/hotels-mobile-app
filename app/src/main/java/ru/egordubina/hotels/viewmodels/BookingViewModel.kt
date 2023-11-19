package ru.egordubina.hotels.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.egordubina.domain.usecases.LoadBookingInfoUseCase
import ru.egordubina.hotels.models.TouristUi
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
    private var job: Job? = null

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
                        touristsList = emptyList()
                    )
                }
            } catch (e: Exception) {
                _uiState.update { BookingScreenUiState.Error }
            }
        }
    }

    fun bookingPay() {
        _uiState.update { BookingScreenUiState.LoadingPay }
        job?.cancel()
        job = viewModelScope.launch {
            delay(2000) // имитация загрузкии оплаты
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

    fun addTourist() {
        viewModelScope.launch {
            val updatedTouristMap = (_uiState.value as BookingScreenUiState.Content).touristsList.toMutableList()
            updatedTouristMap.add(TouristUi(Random.nextInt(0, 1000)))
            _uiState.update {
                (it as BookingScreenUiState.Content).copy(touristsList = updatedTouristMap)
            }
        }
    }

    fun collapseTouristCard(touristCardPosition: Int, collapse: Boolean) {
//        viewModelScope.launch {
//            if (collapse)
//                _uiState.update {
//                    (it as BookingScreenUiState.Content).copy(
//                        touristsList = it.touristsList.map { touristUi ->
//                            touristUi.copy(isVisible = false)
//                        }
//                    )
//                }
//            else
//                _uiState.update {
//                    (it as BookingScreenUiState.Content).copy(
//                        touristsList = it.touristsList.mapIndexed { index, touristUi ->
//                            if (index == touristCardPosition)
//                                touristUi.copy(isVisible = true)
//                            else
//                                touristUi.copy(isVisible = false)
//                        }
//                    )
//                }
//        }
    }
}