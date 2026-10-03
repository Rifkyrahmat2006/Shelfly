package com.shelfly.app.feature.search

import androidx.lifecycle.ViewModel
import com.shelfly.app.data.local.entity.MaterialEntity
import com.shelfly.app.data.repository.MaterialRepository
import com.shelfly.app.feature.state.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// PIC: Person D — Search ViewModel (PRD section 19). Query case-insensitive
// (SQLite LIKE default sudah case-insensitive untuk ASCII).
class SearchViewModel(private val repository: MaterialRepository) : ViewModel() {

    fun search(query: String): Flow<UiState<List<MaterialEntity>>> {
        if (query.isBlank()) {
            return kotlinx.coroutines.flow.flowOf(UiState.Empty)
        }
        return repository.search(query).map { results ->
            if (results.isEmpty()) UiState.Empty else UiState.Success(results)
        }
    }
}
