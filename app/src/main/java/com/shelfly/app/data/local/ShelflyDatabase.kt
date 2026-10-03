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
// PIC: Person B — Migration strategy (PRD section 35)
// Saat ini version = 1, exportSchema = false (project kuliah, belum butuh backward-compat prod).
// Kalau nanti ada perubahan schema (tambah/ubah kolom Entity):
//   1. Naikkan `version` di atas (misal 1 -> 2)
//   2. Tulis Migration object: object MIGRATION_1_2 : Migration(1, 2) { override fun migrate(db: SupportSQLiteDatabase) { db.execSQL("ALTER TABLE ...") } }
//   3. Daftarkan ke builder: .addMigrations(MIGRATION_1_2) di getInstance()
//   4. Set exportSchema = true + tentukan schemaLocation kalau butuh test migration otomatis
// ponytail: fallbackToDestructiveMigration() TIDAK dipakai sengaja — itu hapus semua data
// user kalau schema berubah tanpa migration, gak boleh buat app yang beneran dipakai.
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
