package com.shelfly.app.data.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shelfly.app.data.local.ShelflyDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * TDD B2 — ShelfRepository, alur RED-GREEN-REFACTOR (docs/TASKS.md).
 * Jalankan: ./gradlew connectedAndroidTest --tests "com.shelfly.app.data.repository.ShelfRepositoryTest"
 */
@RunWith(AndroidJUnit4::class)
class ShelfRepositoryTest {

    private lateinit var db: ShelflyDatabase
    private lateinit var repository: ShelfRepository

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShelflyDatabase::class.java
        ).allowMainThreadQueries().build()
        repository = ShelfRepository(db.shelfDao())
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun createShelf_thenGetAllShelves_returnsIt() = runBlocking {
        repository.createShelf(name = "Pemrograman Mobile", description = "Materi S5", icon = "book")

        val result = repository.getAllShelves().first()

        assertEquals(1, result.size)
        assertEquals("Pemrograman Mobile", result[0].name)
    }

    @Test
    fun createShelf_blankName_throwsIllegalArgumentException() = runBlocking {
        try {
            repository.createShelf(name = "   ")
            assertTrue("Harusnya lempar IllegalArgumentException buat nama kosong", false)
        } catch (e: IllegalArgumentException) {
            // expected
        }
    }

    @Test
    fun getShelfById_existingId_returnsShelf() = runBlocking {
        val id = repository.createShelf(name = "Basis Data")

        val result = repository.getShelfById(id)

        assertEquals("Basis Data", result?.name)
    }

    @Test
    fun getShelfById_unknownId_returnsNull() = runBlocking {
        val result = repository.getShelfById(9999L)

        assertNull(result)
    }

    @Test
    fun renameShelf_updatesName() = runBlocking {
        val id = repository.createShelf(name = "Old Name")
        val shelf = repository.getShelfById(id)!!

        repository.renameShelf(shelf, "New Name")
        val result = repository.getShelfById(id)

        assertEquals("New Name", result?.name)
    }

    @Test
    fun deleteShelf_removesFromGetAllShelves() = runBlocking {
        val id = repository.createShelf(name = "Temp Shelf")
        val shelf = repository.getShelfById(id)!!

        repository.deleteShelf(shelf)
        val result = repository.getAllShelves().first()

        assertTrue(result.isEmpty())
    }
}
