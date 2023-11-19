package ru.egordubina.hotels.viewmodels

import kotlinx.coroutines.flow.StateFlow

/**
 * Интерфейс описывающий базовую ViewModel
 * @property uiState - UI состояние экрана, передается в StateFlow
 * @property loadData - функциия для загрузки данных в uiState
 * */
interface IViewModel<T> {
    val uiState: StateFlow<T>
    fun loadData()
}
