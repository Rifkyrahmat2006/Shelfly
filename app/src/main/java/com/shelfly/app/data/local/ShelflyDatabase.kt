package com.shelfly.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shelfly.app.data.local.dao.CategoryDao
import com.shelfly.app.data.local.dao.MaterialDao
import com.shelfly.app.data.local.dao.ShelfDao
import com.shelfly.app.data.local.entity.CategoryEntity
import com.shelfly.app.data.local.entity.MaterialEntity
import com.shelfly.app.data.local.entity.ShelfEntity

@Database(
    entities = [ShelfEntity::class, CategoryEntity::class, MaterialEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class ShelflyDatabase : RoomDatabase() {
    abstract fun shelfDao(): ShelfDao
    abstract fun materialDao(): MaterialDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile private var INSTANCE: ShelflyDatabase? = null

        fun getInstance(context: Context): ShelflyDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ShelflyDatabase::class.java,
                    "shelfly.db",
                ).build().also { INSTANCE = it }
            }
    }
}
