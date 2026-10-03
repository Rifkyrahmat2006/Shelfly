package com.shelfly.app.feature.home

import androidx.lifecycle.ViewModel
import com.shelfly.app.data.local.dao.ShelfWithCount
import com.shelfly.app.data.local.entity.MaterialEntity
import com.shelfly.app.data.repository.MaterialRepository
import com.shelfly.app.data.repository.ShelfRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

// PIC: Person A - Home state (PRD section 41), gantikan dummyShelves/dummyRecent
data class HomeUiState(
    val shelves: List<ShelfWithCount> = emptyList(),
    val recent: List<MaterialEntity> = emptyList(),
)

class HomeViewModel(
    private val shelfRepository: ShelfRepository,
    private val materialRepository: MaterialRepository,
) : ViewModel() {
    fun observeHome(): Flow<HomeUiState> =
        shelfRepository.getShelfWithMaterialCount().combine(materialRepository.getRecent()) { shelves, recent ->
            HomeUiState(shelves = shelves, recent = recent.take(5))
        }
}
