package com.shelfly.app.feature.shelf

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.shelfly.app.data.repository.ShelfRepository

// PIC: Person A — Create/Edit Shelf form (PRD section 17, design.md §5)
class ShelfFormViewModel(
    private val repository: ShelfRepository,
    private val existingShelfId: Long? = null,
) : ViewModel() {
    val name = mutableStateOf("")
    val description = mutableStateOf("")
    val icon = mutableStateOf<String?>(null)
    val errorMessage = mutableStateOf<String?>(null)

    suspend fun loadExisting() {
        val id = existingShelfId ?: return
        val shelf = repository.getShelfById(id) ?: return
        name.value = shelf.name
        description.value = shelf.description ?: ""
        icon.value = shelf.icon
    }

    suspend fun save(): Boolean {
        if (name.value.isBlank()) {
            errorMessage.value = "Nama Shelf tidak boleh kosong"
            return false
        }
        return try {
            if (existingShelfId == null) {
                repository.createShelf(name.value, description.value.ifBlank { null }, icon.value)
            } else {
                val shelf = repository.getShelfById(existingShelfId) ?: return false
                repository.updateShelf(
                    shelf.copy(
                        name = name.value.trim(),
                        description = description.value.ifBlank { null },
                        icon = icon.value,
                    )
                )
            }
            errorMessage.value = null
            true
        } catch (e: IllegalArgumentException) {
            errorMessage.value = e.message
            false
        }
    }
}
