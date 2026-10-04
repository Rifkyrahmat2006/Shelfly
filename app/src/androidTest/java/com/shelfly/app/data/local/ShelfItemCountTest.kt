package com.shelfly.app.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shelfly.app.data.local.entity.MaterialEntity
import com.shelfly.app.data.local.entity.ShelfEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals

@RunWith(AndroidJUnit4::class)
class ShelfItemCountTest {

    private lateinit var db: ShelflyDatabase

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShelflyDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun shelfWithNMaterials_countMatchesN() = runBlocking {
        val shelfId = db.shelfDao().insert(ShelfEntity(name = "Semester 5"))
        val otherShelfId = db.shelfDao().insert(ShelfEntity(name = "Semester 6"))

        repeat(3) { i ->
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
                title = "Materi lain",
                fileUri = "content://fake/other",
                fileType = "pdf",
                fileSize = 512L,
                shelfId = otherShelfId,
            )
        )

        val result = db.shelfDao().getShelfWithMaterialCount().first()
        val target = result.first { it.shelf.id == shelfId }

        assertEquals(3, target.materialCount)
    }

    @Test
    fun shelfWithoutMaterials_countIsZero() = runBlocking {
        val shelfId = db.shelfDao().insert(ShelfEntity(name = "Kosong"))

        val result = db.shelfDao().getShelfWithMaterialCount().first()
        val target = result.first { it.shelf.id == shelfId }

        assertEquals(0, target.materialCount)
    }
}
