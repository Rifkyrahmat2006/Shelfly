package com.shelfly.app.feature.material

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.shelfly.app.data.repository.MaterialRepository

// PIC: Person C — Add Material flow ViewModel (PRD §18, Add Material Flow design.md §5).
// Context disimpan sebagai applicationContext (bukan Activity context) supaya aman dari leak.
class AddMaterialViewModel(
    private val repository: MaterialRepository,
    context: Context,
) : ViewModel() {

    private val appContext = context.applicationContext

    val title = mutableStateOf("")
    private var selectedUri: Uri? = null
    private var fileType: String = ""
    private var fileSize: Long = 0
    private var shelfId: Long? = null
    private var categoryId: Long? = null

    fun onFileSelected(uri: Uri) {
        selectedUri = uri
        val metadata = extractFileMetadata(appContext, uri)
        title.value = metadata.title
        fileType = metadata.fileType
        fileSize = metadata.fileSize
        persistUriPermission(appContext, uri)
    }

    fun onShelfSelected(id: Long) {
        shelfId = id
    }

    fun onCategorySelected(id: Long?) {
        categoryId = id
    }

    suspend fun save() {
        val uri = selectedUri ?: return
        val shelf = shelfId ?: return
        check(shelf > 0) { "Tidak ada Shelf dipilih. Buat Shelf terlebih dahulu sebelum menambah Material." }
        repository.importMaterial(
            title = title.value,
            fileUri = uri.toString(),
            fileType = fileType,
            fileSize = fileSize,
            shelfId = shelf,
            categoryId = categoryId,
        )
    }
}