package com.shelfly.app.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shelfly.app.data.local.dao.CategoryDao
import com.shelfly.app.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

@RunWith(AndroidJUnit4::class)
class CategoryDaoTest {

    private lateinit var db: ShelflyDatabase
    private lateinit var dao: CategoryDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShelflyDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = db.categoryDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertCategory_thenGetAll_returnsInsertedCategory() = runBlocking {
        dao.insert(CategoryEntity(name = "Modul"))
        val result = dao.getAll().first()

        assertEquals(1, result.size)
        assertEquals("Modul", result[0].name)
    }

    @Test
    fun deleteCategory_removesFromGetAll() = runBlocking {
        dao.insert(CategoryEntity(name = "Referensi"))
        val inserted = dao.getAll().first().first()

        dao.delete(inserted)
        val result = dao.getAll().first()

        assertTrue(result.isEmpty())
    }
}
