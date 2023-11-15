package ru.egordubina.hotels.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.egordubina.domain.usecases.LoadHotelUseCase
import ru.egordubina.hotels.uistates.HotelScreenUiState
import javax.inject.Inject

@HiltViewModel
class HotelScreenViewModel @Inject constructor(
    private val loadHotelUseCase: LoadHotelUseCase
) : ViewModel() {
    private var _uiState: MutableStateFlow<HotelScreenUiState> =
        MutableStateFlow(HotelScreenUiState.Loading)
    val uiState: StateFlow<HotelScreenUiState> = _uiState.asStateFlow()
    private var job: Job? = null

    init {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            try {
                // todo: ищменитть модель с domain на ui и сделать маппер
                val result = loadHotelUseCase.loadHotel()
                Log.d("RESULT", result.toString())
                _uiState.value = HotelScreenUiState.Content(
                    name = result.name,
                    address = result.address,
                    price = result.minimalPrice,
                    priceLabel = result.priceForIt,
                    rating = result.rating,
                    ratingName = result.ratingName
                )
            } catch (e: Exception) {
                Log.e("RESULT", e.toString())
                _uiState.value = HotelScreenUiState.Error
            }
        }
    }
}