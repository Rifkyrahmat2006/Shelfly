package com.shelfly.app.feature.shelves

import androidx.lifecycle.ViewModel
import com.shelfly.app.data.local.dao.ShelfWithCount
import com.shelfly.app.data.local.entity.ShelfEntity
import com.shelfly.app.data.repository.ShelfRepository
import com.shelfly.app.feature.state.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// PIC: Person A — All Shelves list state (PRD section 41 pt 2)
class ShelvesViewModel(private val repository: ShelfRepository) : ViewModel() {
    suspend fun deleteShelf(shelf: ShelfEntity) = repository.deleteShelf(shelf)

    fun observeShelves(): Flow<UiState<List<ShelfWithCount>>> =
        repository.getShelfWithMaterialCount().map { list ->
            if (list.isEmpty()) UiState.Empty else UiState.Success(list)
        }
}
