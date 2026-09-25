package com.shelfly.app.data.repository

import com.shelfly.app.data.local.dao.CategoryDao
import com.shelfly.app.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

// PIC: Person B — Category CRUD (PRD section 35, 11)
class CategoryRepository(private val categoryDao: CategoryDao) {
    fun getAllCategories(): Flow<List<CategoryEntity>> = categoryDao.getAll()

    suspend fun getCategoryById(id: Long): CategoryEntity? = categoryDao.getById(id)

    suspend fun createCategory(name: String): Long {
        require(name.isNotBlank()) { "Nama Category tidak boleh kosong" }
        return categoryDao.insert(CategoryEntity(name = name.trim()))
    }

    suspend fun renameCategory(category: CategoryEntity, newName: String) {
        require(newName.isNotBlank()) { "Nama Category tidak boleh kosong" }
        categoryDao.update(category.copy(name = newName.trim()))
    }

    suspend fun deleteCategory(category: CategoryEntity) = categoryDao.delete(category)
}
