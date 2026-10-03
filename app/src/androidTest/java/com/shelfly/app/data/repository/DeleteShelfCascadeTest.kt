package com.shelfly.app.data.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shelfly.app.data.local.ShelflyDatabase
import com.shelfly.app.data.local.entity.MaterialEntity
import com.shelfly.app.data.local.entity.ShelfEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

@RunWith(AndroidJUnit4::class)
class DeleteShelfCascadeTest {

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
    fun deleteShelf_cascadesDeleteToItsMaterials() = runBlocking {
        val shelfId = db.shelfDao().insert(ShelfEntity(name = "Semester 5"))
        val otherShelfId = db.shelfDao().insert(ShelfEntity(name = "Semester 6"))

        repeat(2) { i ->
            db.materialDao().insert(
                MaterialEntity(
                    title = "Materi $i",
                    fileUri = "content://fake/$i",
                    fileType = "pdf",
                    fileSize = 1024L,
                    shelfId = shelfId,
                )
            )
        }

        db.materialDao().insert(
            MaterialEntity(
                title = "Materi aman",
                fileUri = "content://fake/safe",
                fileType = "pdf",
                fileSize = 512L,
                shelfId = otherShelfId,
            )
        )

        val allMaterialsBefore = db.materialDao().getAll().first()
        assertEquals(3, allMaterialsBefore.size)

        val shelfToDelete = repository.getShelfById(shelfId)!!
        repository.deleteShelf(shelfToDelete)

        val allMaterialsAfter = db.materialDao().getAll().first()
        assertEquals(1, allMaterialsAfter.size)
        assertTrue(allMaterialsAfter.all { it.shelfId == otherShelfId })
    }
}
