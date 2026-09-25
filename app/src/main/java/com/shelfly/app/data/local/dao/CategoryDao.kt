package com.shelfly.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.shelfly.app.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

// PIC: Person B — Category (PRD section 35)
@Dao
interface CategoryDao {
    @Query("SELECT * FROM category ORDER BY name ASC")
    fun getAll(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM category WHERE id = :id")
    suspend fun getById(id: Long): CategoryEntity?

    @Insert
    suspend fun insert(category: CategoryEntity): Long

    @Update
    suspend fun update(category: CategoryEntity)

    @Delete
    suspend fun delete(category: CategoryEntity)
}
