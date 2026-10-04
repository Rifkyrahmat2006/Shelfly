package com.shelfly.app.feature.material

import android.content.Context
import androidx.core.content.FileProvider
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shelfly.app.data.local.ShelflyDatabase
import com.shelfly.app.data.local.entity.ShelfEntity
import com.shelfly.app.data.repository.MaterialRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import java.io.File

@RunWith(AndroidJUnit4::class)
class AddMaterialViewModelTest {

    private lateinit var db: ShelflyDatabase
    private lateinit var repository: MaterialRepository
    private lateinit var viewModel: AddMaterialViewModel
    private var shelfId: Long = 0
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(
            context,
            ShelflyDatabase::class.java
        ).allowMainThreadQueries().build()
        repository = MaterialRepository(db.materialDao())
        viewModel = AddMaterialViewModel(repository, context)
        shelfId = runBlocking { db.shelfDao().insert(ShelfEntity(name = "Semester 5")) }
    }

    @After
    fun tearDown() {
        db.close()
    }

    private fun createTestFileUri(name: String = "modul1.pdf"): android.net.Uri {
        val file = File(context.cacheDir, name)
        file.writeText("dummy content")
        return FileProvider.getUriForFile(context, "com.shelfly.app.fileprovider", file)
    }

    @Test
    fun onFileSelected_extractsMetadataAndPrefillsTitle() {
        viewModel.onFileSelected(createTestFileUri("modul1.pdf"))

        assertEquals("modul1.pdf", viewModel.title.value)
    }

    @Test
    fun save_withShelfSelected_persistsToRepository() = runBlocking {
        viewModel.onFileSelected(createTestFileUri("modul1.pdf"))
        viewModel.onShelfSelected(shelfId)

        viewModel.save()

        val saved = db.materialDao().getAll().first()
        assertEquals(1, saved.size)
        assertEquals("modul1.pdf", saved[0].title)
    }

    @Test
    fun save_withoutFileSelected_doesNothing() = runBlocking {
        viewModel.onShelfSelected(shelfId)

        viewModel.save()

        val saved = db.materialDao().getAll().first()
        assertTrue(saved.isEmpty())
    }
}
