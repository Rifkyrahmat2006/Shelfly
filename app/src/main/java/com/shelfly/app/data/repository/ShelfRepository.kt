package com.shelfly.app.data.repository

import com.shelfly.app.data.local.dao.ShelfDao
import com.shelfly.app.data.local.entity.ShelfEntity
import kotlinx.coroutines.flow.Flow

// PIC: Person B — abstraksi antara UI/business logic dan Room (PRD section 31)
class ShelfRepository(private val shelfDao: ShelfDao) {
    fun getAllShelves(): Flow<List<ShelfEntity>> = shelfDao.getAll()

    suspend fun createShelf(name: String, description: String? = null, icon: String? = null): Long =
        shelfDao.insert(ShelfEntity(name = name, description = description, icon = icon))

    suspend fun renameShelf(shelf: ShelfEntity, newName: String) =
        shelfDao.update(shelf.copy(name = newName))

    suspend fun deleteShelf(shelf: ShelfEntity) = shelfDao.delete(shelf)
}
