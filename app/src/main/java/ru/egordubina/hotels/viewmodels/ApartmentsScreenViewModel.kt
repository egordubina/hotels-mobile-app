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
import ru.egordubina.domain.usecases.LoadApartmentsUseCase
import ru.egordubina.hotels.uistates.ApartmentsScreenUiState
import javax.inject.Inject

@HiltViewModel
class ApartmentsScreenViewModel @Inject constructor(
    private val loadApartmentsUseCase: LoadApartmentsUseCase
): ViewModel() {
    private val _uiState: MutableStateFlow<ApartmentsScreenUiState> = MutableStateFlow(ApartmentsScreenUiState.Loading)
    val uiState: StateFlow<ApartmentsScreenUiState> = _uiState.asStateFlow()
    private var job: Job? = null

    init {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = ApartmentsScreenUiState.Loading
            try {
                val response = loadApartmentsUseCase.loadApartments()
                _uiState.value = ApartmentsScreenUiState.Content(apartments = response)
            } catch (e: Exception) {
                _uiState.value = ApartmentsScreenUiState.Error
            }
        }
    }
}