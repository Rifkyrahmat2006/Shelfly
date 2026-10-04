package com.shelfly.app.feature.shelf

import androidx.lifecycle.ViewModel
import com.shelfly.app.data.local.entity.MaterialEntity
import com.shelfly.app.data.local.entity.ShelfEntity
import com.shelfly.app.data.repository.MaterialRepository
import com.shelfly.app.data.repository.ShelfRepository
import com.shelfly.app.feature.state.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// PIC: Person B — Shelf detail + material list (PRD section 17, 18)
class ShelfDetailViewModel(
    private val shelfRepository: ShelfRepository,
    private val materialRepository: MaterialRepository,
) : ViewModel() {
    suspend fun loadShelf(shelfId: Long): ShelfEntity? = shelfRepository.getShelfById(shelfId)

    suspend fun deleteShelf(shelf: ShelfEntity) = shelfRepository.deleteShelf(shelf)

    suspend fun deleteMaterial(material: MaterialEntity) = materialRepository.delete(material)

    fun observeMaterials(shelfId: Long): Flow<UiState<List<MaterialEntity>>> =
        materialRepository.getByShelf(shelfId).map { list ->
            if (list.isEmpty()) UiState.Empty else UiState.Success(list)
        }
}
