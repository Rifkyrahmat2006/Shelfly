package com.shelfly.app.data.repository

import com.shelfly.app.data.local.dao.ShelfDao
import com.shelfly.app.data.local.entity.ShelfEntity
import kotlinx.coroutines.flow.Flow

// PIC: Person B — abstraksi antara UI/business logic dan Room (PRD section 31)
class ShelfRepository(private val shelfDao: ShelfDao) {
    fun getAllShelves(): Flow<List<ShelfEntity>> = shelfDao.getAll()

    suspend fun getShelfById(id: Long): ShelfEntity? = shelfDao.getById(id)

    suspend fun createShelf(name: String, description: String? = null, icon: String? = null): Long {
        require(name.isNotBlank()) { "Nama Shelf tidak boleh kosong" }
        return shelfDao.insert(ShelfEntity(name = name.trim(), description = description, icon = icon))
    }

    suspend fun renameShelf(shelf: ShelfEntity, newName: String) {
        require(newName.isNotBlank()) { "Nama Shelf tidak boleh kosong" }
        shelfDao.update(shelf.copy(name = newName.trim()))
    }

    suspend fun updateShelf(shelf: ShelfEntity) {
        require(shelf.name.isNotBlank()) { "Nama Shelf tidak boleh kosong" }
        shelfDao.update(shelf)
    }

    suspend fun deleteShelf(shelf: ShelfEntity) = shelfDao.delete(shelf)
}
