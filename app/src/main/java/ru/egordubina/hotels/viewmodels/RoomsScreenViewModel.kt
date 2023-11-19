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
import ru.egordubina.hotels.models.asUi
import ru.egordubina.hotels.uistates.RoomsScreenUiState
import javax.inject.Inject

@HiltViewModel
class RoomsScreenViewModel @Inject constructor(
    private val loadApartmentsUseCase: LoadApartmentsUseCase
): ViewModel(), IViewModel<RoomsScreenUiState> {
    private val _uiState: MutableStateFlow<RoomsScreenUiState> = MutableStateFlow(RoomsScreenUiState.Loading)
    override val uiState: StateFlow<RoomsScreenUiState> = _uiState.asStateFlow()
    private var job: Job? = null

    init {
        loadData()
    }

    override fun loadData() {
        _uiState.value = RoomsScreenUiState.Loading
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = loadApartmentsUseCase.loadRooms().asUi()
                _uiState.value = RoomsScreenUiState.Content(apartments = response)
            } catch (e: Exception) {
                _uiState.value = RoomsScreenUiState.Error
            }
        }
    }
}