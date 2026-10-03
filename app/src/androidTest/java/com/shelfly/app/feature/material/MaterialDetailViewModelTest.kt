package com.shelfly.app.feature.material

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shelfly.app.data.local.ShelflyDatabase
import com.shelfly.app.data.local.entity.ShelfEntity
import com.shelfly.app.data.repository.MaterialRepository
import com.shelfly.app.data.repository.ShelfRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull

@RunWith(AndroidJUnit4::class)
class MaterialDetailViewModelTest {

    private lateinit var db: ShelflyDatabase
    private lateinit var materialRepository: MaterialRepository
    private lateinit var shelfRepository: ShelfRepository

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, ShelflyDatabase::class.java)
            .allowMainThreadQueries().build()
        materialRepository = MaterialRepository(db.materialDao())
        shelfRepository = ShelfRepository(db.shelfDao())
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun material_existingId_returnsMaterialEntity() = runBlocking {
        val shelfId = shelfRepository.createShelf(name = "Semester 5")
        val materialId = materialRepository.importMaterial(
            title = "Materi.pdf",
            fileUri = "content://fake/materi.pdf",
            fileType = "application/pdf",
            fileSize = 1024L,
            shelfId = shelfId,
        )
        val viewModel = MaterialDetailViewModel(materialRepository)

        val result = viewModel.material(materialId).first()

        assertEquals("Materi.pdf", result?.title)
    }

    @Test
    fun material_nonExistingId_returnsNull() = runBlocking {
        val viewModel = MaterialDetailViewModel(materialRepository)

        val result = viewModel.material(999L).first()

        assertNull(result)
    }
}
