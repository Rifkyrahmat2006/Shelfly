package com.shelfly.app.data.repository

import com.shelfly.app.data.local.dao.MaterialDao
import com.shelfly.app.data.local.entity.MaterialEntity
import kotlinx.coroutines.flow.Flow

// PIC: Person C (import/CRUD) & Person D (search/filter/favorite/recent)
class MaterialRepository(private val materialDao: MaterialDao) {
    fun getByShelf(shelfId: Long): Flow<List<MaterialEntity>> = materialDao.getByShelf(shelfId)
    suspend fun getById(id: Long): MaterialEntity? = materialDao.getById(id)
    fun search(query: String): Flow<List<MaterialEntity>> = materialDao.search(query)
    fun getFavorites(): Flow<List<MaterialEntity>> = materialDao.getFavorites()
    fun getRecent(): Flow<List<MaterialEntity>> = materialDao.getRecent()

    suspend fun importMaterial(
        title: String,
        fileUri: String,
        fileType: String,
        fileSize: Long,
        shelfId: Long,
        categoryId: Long? = null,
    ): Long {
        require(title.isNotBlank()) { "Judul Material tidak boleh kosong" }
        require(fileUri.isNotBlank()) { "fileUri tidak boleh kosong" }
        return materialDao.insert(
            MaterialEntity(
                title = title.trim(),
                fileUri = fileUri,
                fileType = fileType,
                fileSize = fileSize,
                shelfId = shelfId,
                categoryId = categoryId,
            )
        )
    }

    suspend fun toggleFavorite(material: MaterialEntity) =
        materialDao.update(material.copy(isFavorite = !material.isFavorite))

    suspend fun markOpened(material: MaterialEntity) =
        materialDao.update(material.copy(lastOpenedAt = System.currentTimeMillis()))

    suspend fun delete(material: MaterialEntity) = materialDao.delete(material)

    // PIC: Person C — Move Material antar Shelf (PRD section 26)
    suspend fun moveToShelf(materialId: Long, targetShelfId: Long) {
        val material = materialDao.getById(materialId) ?: return
        materialDao.update(material.copy(shelfId = targetShelfId))
    }
}
