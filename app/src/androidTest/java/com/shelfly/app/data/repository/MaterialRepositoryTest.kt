package com.shelfly.app.data.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shelfly.app.data.local.ShelflyDatabase
import com.shelfly.app.data.local.entity.ShelfEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows

@RunWith(AndroidJUnit4::class)
class MaterialRepositoryTest {

    private lateinit var db: ShelflyDatabase
    private lateinit var repository: MaterialRepository
    private var shelfId: Long = 0

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShelflyDatabase::class.java
        ).allowMainThreadQueries().build()
        repository = MaterialRepository(db.materialDao())
        shelfId = runBlocking { db.shelfDao().insert(ShelfEntity(name = "Semester 5")) }
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun importMaterial_validData_savedAndRetrievable() = runBlocking {
        repository.importMaterial(
            title = "Database Room.pdf",
            fileUri = "content://fake/file1",
            fileType = "pdf",
            fileSize = 2048L,
            shelfId = shelfId,
        )

        val result = repository.getByShelf(shelfId).first()
        assertEquals(1, result.size)
        assertEquals("Database Room.pdf", result[0].title)
    }

    @Test
    fun importMaterial_blankTitle_throws() {
        assertThrows(IllegalArgumentException::class.java) {
            runBlocking {
                repository.importMaterial(
                    title = "   ",
                    fileUri = "content://fake/file1",
                    fileType = "pdf",
                    fileSize = 1024L,
                    shelfId = shelfId,
                )
            }
        }
    }

    @Test
    fun moveToShelf_validTargetShelf_updatesShelfId() = runBlocking {
        val id = repository.importMaterial(
            title = "Materi",
            fileUri = "content://fake/file1",
            fileType = "pdf",
            fileSize = 1024L,
            shelfId = shelfId,
        )
        val targetShelfId = db.shelfDao().insert(ShelfEntity(name = "Target Shelf"))

        repository.moveToShelf(id, targetShelfId)

        val inOld = repository.getByShelf(shelfId).first()
        val inNew = repository.getByShelf(targetShelfId).first()
        assertEquals(0, inOld.size)
        assertEquals(1, inNew.size)
    }

    @Test
    fun importMaterial_blankFileUri_throws() {
        assertThrows(IllegalArgumentException::class.java) {
            runBlocking {
                repository.importMaterial(
                    title = "Materi",
                    fileUri = "",
                    fileType = "pdf",
                    fileSize = 1024L,
                    shelfId = shelfId,
                )
            }
        }
    }
}
