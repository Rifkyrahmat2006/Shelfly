package com.shelfly.app.feature.shelf

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shelfly.app.data.local.ShelflyDatabase
import com.shelfly.app.data.repository.ShelfRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

@RunWith(AndroidJUnit4::class)
class ShelfFormViewModelTest {

    private lateinit var db: ShelflyDatabase
    private lateinit var repository: ShelfRepository

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, ShelflyDatabase::class.java)
            .allowMainThreadQueries().build()
        repository = ShelfRepository(db.shelfDao())
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun save_createMode_validName_insertsShelf() = runBlocking {
        val viewModel = ShelfFormViewModel(repository, existingShelfId = null)
        viewModel.name.value = "Pemrograman Mobile"

        val result = viewModel.save()

        assertTrue(result)
        val shelves = repository.getAllShelves().first()
        assertEquals(1, shelves.size)
        assertEquals("Pemrograman Mobile", shelves[0].name)
    }

    @Test
    fun save_createMode_blankName_returnsFalseNoInsert() = runBlocking {
        val viewModel = ShelfFormViewModel(repository, existingShelfId = null)
        viewModel.name.value = "   "

        val result = viewModel.save()

        assertTrue(!result)
        assertEquals("Nama Shelf tidak boleh kosong", viewModel.errorMessage.value)
        assertEquals(0, repository.getAllShelves().first().size)
    }

    @Test
    fun loadExisting_editMode_prefillsNameAndDescription() = runBlocking {
        val id = repository.createShelf(name = "Basis Data", description = "Semester 5")
        val viewModel = ShelfFormViewModel(repository, existingShelfId = id)

        viewModel.loadExisting()

        assertEquals("Basis Data", viewModel.name.value)
        assertEquals("Semester 5", viewModel.description.value)
    }

    @Test
    fun save_editMode_validName_updatesShelf() = runBlocking {
        val id = repository.createShelf(name = "Basis Data")
        val viewModel = ShelfFormViewModel(repository, existingShelfId = id)
        viewModel.loadExisting()
        viewModel.name.value = "Basis Data Lanjut"

        val result = viewModel.save()

        assertTrue(result)
        val updated = repository.getShelfById(id)
        assertEquals("Basis Data Lanjut", updated?.name)
    }
}
