package com.shelfly.app.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shelfly.app.data.local.dao.ShelfDao
import com.shelfly.app.data.local.entity.ShelfEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

/**
 * Contoh test DAO — dipakai sebagai template TDD buat Person B (Database & Shelf).
 *
 * Alur RED-GREEN-REFACTOR:
 * 1. RED   : tulis test ini duluan, jalankan -> gagal (ShelfDao/Entity belum ada atau method belum ada).
 * 2. GREEN : tulis kode minimal di ShelfDao/ShelflyDatabase biar test ini lolos.
 * 3. REFACTOR: rapikan tanpa mengubah perilaku, test harus tetap hijau.
 *
 * Jalankan: perlu emulator/device nyala (instrumented test, bukan JVM unit test)
 *   ./gradlew connectedAndroidTest --tests "com.shelfly.app.data.local.ShelfDaoTest"
 */
@RunWith(AndroidJUnit4::class)
class ShelfDaoTest {

    private lateinit var db: ShelflyDatabase
    private lateinit var dao: ShelfDao

    @Before
    fun setUp() {
        // In-memory DB: reset tiap test run, tidak nyentuh data asli device
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShelflyDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = db.shelfDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertShelf_thenGetAllShelves_returnsInsertedShelf() = runBlocking {
        val shelf = ShelfEntity(
            name = "Pemrograman Mobile",
            description = "Materi kuliah semester 5",
            icon = "book",
            createdAt = System.currentTimeMillis()
        )

        dao.insert(shelf)
        val result = dao.getAll().first()

        assertEquals(1, result.size)
        assertEquals("Pemrograman Mobile", result[0].name)
    }

    @Test
    fun deleteShelf_removesFromGetAllShelves() = runBlocking {
        val shelf = ShelfEntity(
            name = "Temp Shelf",
            description = null,
            icon = "folder",
            createdAt = System.currentTimeMillis()
        )
        dao.insert(shelf)
        val inserted = dao.getAll().first().first()

        dao.delete(inserted)
        val result = dao.getAll().first()

        assertTrue(result.isEmpty())
    }
}
