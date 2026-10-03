package com.shelfly.app.feature.favorites

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
class FavoritesViewModelTest {

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
    fun noFavorites_returnsEmpty() = runBlocking {
        repository.importMaterial(
            title = "Materi 1", fileUri = "u1", fileType = "pdf",
            fileSize = 100L, shelfId = shelfId,
        )
        val viewModel = FavoritesViewModel(repository)

        val state = viewModel.favorites().first()

        assertTrue(state is UiState.Empty)
    }

    @Test
    fun withFavorites_returnsSuccessWithOnlyFavorited() = runBlocking {
        val id1 = repository.importMaterial(
            title = "Materi 1", fileUri = "u1", fileType = "pdf",
            fileSize = 100L, shelfId = shelfId,
        )
        repository.importMaterial(
            title = "Materi 2", fileUri = "u2", fileType = "pdf",
            fileSize = 200L, shelfId = shelfId,
        )
        val material1 = MaterialEntity(
            id = id1, title = "Materi 1", fileUri = "u1", fileType = "pdf",
            fileSize = 100L, shelfId = shelfId,
        )
        repository.toggleFavorite(material1)
        val viewModel = FavoritesViewModel(repository)

        val state = viewModel.favorites().first()

        assertTrue(state is UiState.Success)
        assertEquals(1, (state as UiState.Success<List<MaterialEntity>>).data.size)
        assertEquals("Materi 1", state.data[0].title)
    }
}
