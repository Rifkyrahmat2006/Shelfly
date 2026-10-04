package com.shelfly.app.feature.search

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shelfly.app.data.local.ShelflyDatabase
import com.shelfly.app.data.local.entity.MaterialEntity
import com.shelfly.app.data.local.entity.ShelfEntity
import com.shelfly.app.data.repository.MaterialRepository
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
class SearchViewModelTest {

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
    fun search_matchingQuery_returnsSuccessWithResults() = runBlocking {
        repository.importMaterial(
            title = "Database Room.pdf",
            fileUri = "content://fake/1",
            fileType = "pdf",
            fileSize = 1024L,
            shelfId = shelfId,
        )
        val viewModel = SearchViewModel(repository)

        val state = viewModel.search("database").first()

        assertTrue(state is UiState.Success)
        assertEquals(1, (state as UiState.Success<List<MaterialEntity>>).data.size)
    }

    @Test
    fun search_caseInsensitive_stillMatches() = runBlocking {
        repository.importMaterial(
            title = "Database Room.pdf",
            fileUri = "content://fake/1",
            fileType = "pdf",
            fileSize = 1024L,
            shelfId = shelfId,
        )
        val viewModel = SearchViewModel(repository)

        val state = viewModel.search("DATABASE").first()

        assertTrue(state is UiState.Success)
        assertEquals(1, (state as UiState.Success<List<MaterialEntity>>).data.size)
    }

    @Test
    fun search_noMatch_returnsEmpty() = runBlocking {
        repository.importMaterial(
            title = "Database Room.pdf",
            fileUri = "content://fake/1",
            fileType = "pdf",
            fileSize = 1024L,
            shelfId = shelfId,
        )
        val viewModel = SearchViewModel(repository)

        val state = viewModel.search("nonexistent").first()

        assertTrue(state is UiState.Empty)
    }

    @Test
    fun search_blankQuery_returnsEmpty() = runBlocking {
        val viewModel = SearchViewModel(repository)

        val state = viewModel.search("").first()

        assertTrue(state is UiState.Empty)
    }
}
