package com.shelfly.app.feature.favorites

import androidx.lifecycle.ViewModel
import com.shelfly.app.data.local.entity.MaterialEntity
import com.shelfly.app.data.repository.MaterialRepository
import com.shelfly.app.feature.state.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// PIC: Person D — Favorites (PRD section 22)
class FavoritesViewModel(private val repository: MaterialRepository) : ViewModel() {

    fun favorites(): Flow<UiState<List<MaterialEntity>>> =
        repository.getFavorites().map { materials ->
            if (materials.isEmpty()) UiState.Empty else UiState.Success(materials)
        }
}