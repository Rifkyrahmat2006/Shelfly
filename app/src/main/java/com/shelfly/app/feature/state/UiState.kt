package com.shelfly.app.feature.state

// PIC: Person D — UiState sealed class (PRD section 32), kontrak dipakai semua ViewModel
// termasuk Nadine (HomeScreen) untuk ganti dummy data jadi state asli.
sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data object Empty : UiState<Nothing>()
    data class Error(val message: String) : UiState<Nothing>()
}
