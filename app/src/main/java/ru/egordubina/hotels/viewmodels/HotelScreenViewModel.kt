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
import ru.egordubina.hotels.models.asUi
import ru.egordubina.hotels.uistates.HotelScreenUiState
import javax.inject.Inject

@HiltViewModel
class HotelScreenViewModel @Inject constructor(
    private val loadHotelUseCase: LoadHotelUseCase
) : ViewModel(), IViewModel<HotelScreenUiState> {
    private var _uiState: MutableStateFlow<HotelScreenUiState> =
        MutableStateFlow(HotelScreenUiState.Loading)
    override val uiState: StateFlow<HotelScreenUiState> = _uiState.asStateFlow()
    private var job: Job? = null

    init {
        loadData()
    }

    override fun loadData() {
        _uiState.value = HotelScreenUiState.Loading
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = loadHotelUseCase.loadHotel().asUi()
                _uiState.value = HotelScreenUiState.Content(
                    name = result.name,
                    address = result.address,
                    price = result.minimalPrice,
                    priceLabel = result.priceForIt,
                    rating = result.rating,
                    ratingName = result.ratingName,
                    hotelDescription = result.aboutHotel.description,
                    peculiarities = result.aboutHotel.peculiarities,
                    imagesUrls = result.imagesUrls
                )
            } catch (e: Exception) {
                _uiState.value = HotelScreenUiState.Error
            }
        }
    }
}