package com.shelfly.app.feature.recent

import androidx.lifecycle.ViewModel
import com.shelfly.app.data.local.entity.MaterialEntity
import com.shelfly.app.data.repository.MaterialRepository
import com.shelfly.app.feature.state.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// PIC: Person D — Recently Opened (PRD section 23)
class RecentViewModel(private val repository: MaterialRepository) : ViewModel() {

    fun recent(): Flow<UiState<List<MaterialEntity>>> =
        repository.getRecent().map { materials ->
            if (materials.isEmpty()) UiState.Empty else UiState.Success(materials)
        }

    suspend fun markOpened(material: MaterialEntity) = repository.markOpened(material)
}