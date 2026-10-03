package com.shelfly.app.data.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shelfly.app.data.local.ShelflyDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows

@RunWith(AndroidJUnit4::class)
class CategoryRepositoryTest {

    private lateinit var db: ShelflyDatabase
    private lateinit var repository: CategoryRepository

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShelflyDatabase::class.java
        ).allowMainThreadQueries().build()
        repository = CategoryRepository(db.categoryDao())
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun createCategory_thenGetAll_returnsCategory() = runBlocking {
        repository.createCategory("Modul")
        val result = repository.getAllCategories().first()

        assertEquals(1, result.size)
        assertEquals("Modul", result[0].name)
    }

    @Test
    fun createCategory_blankName_throws() {
        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { repository.createCategory("   ") }
        }
    }
}
