package com.shelfly.app.feature.material

import androidx.lifecycle.ViewModel
import com.shelfly.app.data.local.entity.MaterialEntity
import com.shelfly.app.data.repository.MaterialRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

// PIC: Person C — Material Detail state (PRD section 24, 25). Fetch material
// by id, dipakai MaterialDetailScreen buat decide PDF viewer vs Open eksternal.
class MaterialDetailViewModel(private val repository: MaterialRepository) : ViewModel() {
    fun material(materialId: Long): Flow<MaterialEntity?> = flow {
        emit(repository.getById(materialId))
    }

    suspend fun markOpened(material: MaterialEntity) = repository.markOpened(material)
}
