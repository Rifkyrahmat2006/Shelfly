package com.shelfly.app.feature.material

import android.content.Context
import androidx.core.content.FileProvider
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shelfly.app.data.local.ShelflyDatabase
import com.shelfly.app.data.repository.MaterialRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertTrue
import java.io.File

// PIC: Person C — regresi crash "shelfId=0 saat belum ada Shelf" (user report:
// upload material dari Home crash/keluar app). HomeScreen FAB kirim fallback
// shelfId=0L kalau belum ada Shelf, dan 0 bukan FK valid -> SQLiteConstraintException
// tidak ketangkep -> crash. Fix: AddMaterialViewModel.save() validasi shelfId
// nyata ada sebelum insert, lempar IllegalStateException yang bisa ditangkap UI.
@RunWith(AndroidJUnit4::class)
class AddMaterialNoShelfTest {

    private lateinit var db: ShelflyDatabase
    private lateinit var repository: MaterialRepository
    private lateinit var viewModel: AddMaterialViewModel
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, ShelflyDatabase::class.java)
            .allowMainThreadQueries().build()
        repository = MaterialRepository(db.materialDao())
        viewModel = AddMaterialViewModel(repository, context)
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
    fun save_withShelfId0_doesNotCrashAndThrowsHandledException() = runBlocking {
        viewModel.onFileSelected(createTestFileUri())
        viewModel.onShelfSelected(0L)

        var threw = false
        try {
            viewModel.save()
        } catch (e: IllegalStateException) {
            threw = true
        }

        assertTrue("save() dengan shelfId=0 (tidak ada Shelf) harus throw IllegalStateException yang bisa ditangkap, bukan crash SQLiteConstraintException", threw)
        val saved = db.materialDao().getAll().first()
        assertTrue("Tidak boleh ada row ter-insert kalau shelfId invalid", saved.isEmpty())
    }
}
