package com.shelfly.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.shelfly.app.data.local.entity.ShelfEntity
import kotlinx.coroutines.flow.Flow

// PIC: Person B — Shelf item count, hasil JOIN Shelf+Material (PRD section 17, 41)
data class ShelfWithCount(
    @Embedded val shelf: ShelfEntity,
    val materialCount: Int,
)

// PIC: Person B — Shelf CRUD (PRD section 17)
@Dao
interface ShelfDao {
    @Query("SELECT * FROM shelf ORDER BY createdAt DESC")
    fun getAll(): Flow<List<ShelfEntity>>

    @Query("SELECT * FROM shelf WHERE id = :id")
    suspend fun getById(id: Long): ShelfEntity?

    @Query("""
        SELECT shelf.*, COUNT(material.id) AS materialCount
        FROM shelf
        LEFT JOIN material ON material.shelfId = shelf.id
        GROUP BY shelf.id
        ORDER BY shelf.createdAt DESC
    """)
    fun getShelfWithMaterialCount(): Flow<List<ShelfWithCount>>

    @Insert
    suspend fun insert(shelf: ShelfEntity): Long

    @Update
    suspend fun update(shelf: ShelfEntity)

    @Delete
    suspend fun delete(shelf: ShelfEntity)
}
