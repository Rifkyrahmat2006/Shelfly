package com.shelfly.app.feature.shelves

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shelfly.app.data.local.dao.ShelfWithCount
import com.shelfly.app.data.local.ShelflyDatabase
import com.shelfly.app.data.repository.ShelfRepository
import com.shelfly.app.feature.state.UiState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

@RunWith(AndroidJUnit4::class)
class ShelvesViewModelTest {

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
    fun observeShelves_emptyDb_emitsEmpty() = runBlocking {
        val viewModel = ShelvesViewModel(repository)

        val state = viewModel.observeShelves().first()

        assertTrue(state is UiState.Empty)
    }

    @Test
    fun observeShelves_hasData_emitsSuccessWithShelves() = runBlocking {
        repository.createShelf(name = "Pemrograman Mobile")
        repository.createShelf(name = "Basis Data")
        val viewModel = ShelvesViewModel(repository)

        val state = viewModel.observeShelves().first()

        assertTrue(state is UiState.Success<*>)
        val shelves = (state as UiState.Success<List<ShelfWithCount>>).data
        assertEquals(2, shelves.size)
    }
}
