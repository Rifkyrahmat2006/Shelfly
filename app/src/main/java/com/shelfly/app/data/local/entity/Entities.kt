package com.shelfly.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// PIC: Person B — Database Model (PRD section 35)
@Entity(tableName = "shelf")
data class ShelfEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val description: String? = null,
    val icon: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
)

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val createdAt: Long = System.currentTimeMillis(),
)

@Entity(tableName = "material")
data class MaterialEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val fileUri: String,
    val fileType: String,
    val fileSize: Long,
    val shelfId: Long,
    val categoryId: Long? = null,
    val isFavorite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val lastOpenedAt: Long? = null,
)
