package com.shelfly.app.feature.recent

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
class RecentViewModelTest {

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
    fun neverOpened_returnsEmpty() = runBlocking {
        repository.importMaterial(
            title = "Materi 1", fileUri = "u1", fileType = "pdf",
            fileSize = 100L, shelfId = shelfId,
        )
        val viewModel = RecentViewModel(repository)

        val state = viewModel.recent().first()

        assertTrue(state is UiState.Empty)
    }

    @Test
    fun markOpened_appearsInRecent() = runBlocking {
        val id1 = repository.importMaterial(
            title = "Materi 1", fileUri = "u1", fileType = "pdf",
            fileSize = 100L, shelfId = shelfId,
        )
        val material1 = MaterialEntity(
            id = id1, title = "Materi 1", fileUri = "u1", fileType = "pdf",
            fileSize = 100L, shelfId = shelfId,
        )
        val viewModel = RecentViewModel(repository)
        viewModel.markOpened(material1)

        val state = viewModel.recent().first()

        assertTrue(state is UiState.Success)
        assertEquals(1, (state as UiState.Success<List<MaterialEntity>>).data.size)
    }
}