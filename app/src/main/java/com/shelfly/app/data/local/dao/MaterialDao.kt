package com.shelfly.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.shelfly.app.data.local.entity.MaterialEntity
import kotlinx.coroutines.flow.Flow

// PIC: Person C (import/CRUD) & Person D (search/filter/sort/favorite/recent)
@Dao
interface MaterialDao {
    @Query("SELECT * FROM material ORDER BY createdAt DESC")
    fun getAll(): Flow<List<MaterialEntity>>

    @Query("SELECT * FROM material WHERE shelfId = :shelfId ORDER BY createdAt DESC")
    fun getByShelf(shelfId: Long): Flow<List<MaterialEntity>>

    @Query("SELECT * FROM material WHERE title LIKE '%' || :query || '%'")
    fun search(query: String): Flow<List<MaterialEntity>>

    @Query("SELECT * FROM material WHERE isFavorite = 1 ORDER BY createdAt DESC")
    fun getFavorites(): Flow<List<MaterialEntity>>

    @Query("SELECT * FROM material WHERE lastOpenedAt IS NOT NULL ORDER BY lastOpenedAt DESC LIMIT 20")
    fun getRecent(): Flow<List<MaterialEntity>>

    @Query("SELECT * FROM material WHERE id = :id")
    suspend fun getById(id: Long): MaterialEntity?

    @Insert
    suspend fun insert(material: MaterialEntity): Long

    @Update
    suspend fun update(material: MaterialEntity)

    @Delete
    suspend fun delete(material: MaterialEntity)
}
