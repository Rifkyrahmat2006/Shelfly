package com.shelfly.app.feature.material

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.shelfly.app.data.repository.MaterialRepository

// PIC: Person C — Add Material flow ViewModel (PRD §18, Add Material Flow design.md §5).
// Context disimpan sebagai applicationContext (bukan Activity context) supaya aman dari leak.
// Bulk upload (user request): saveBulk() terima banyak Uri sekaligus, tiap file
// title-nya diambil langsung dari nama file asli (gak ada form per-file manual).
class AddMaterialViewModel(
    private val repository: MaterialRepository,
    context: Context,
) : ViewModel() {

    private val appContext = context.applicationContext

    val title = mutableStateOf("")
    private var selectedUri: Uri? = null
    var fileType: String = ""
        private set
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

    // Bulk upload: tiap Uri diimport langsung pakai metadata filenya sendiri
    // (title = nama file asli), tanpa melalui state title/fileType/fileSize
    // single-file di atas. Return jumlah file yang berhasil diimport.
    suspend fun saveBulk(uris: List<Uri>, targetShelfId: Long): Int {
        check(targetShelfId > 0) { "Tidak ada Shelf dipilih. Buat Shelf terlebih dahulu sebelum menambah Material." }
        var successCount = 0
        for (uri in uris) {
            persistUriPermission(appContext, uri)
            val metadata = extractFileMetadata(appContext, uri)
            repository.importMaterial(
                title = metadata.title,
                fileUri = uri.toString(),
                fileType = metadata.fileType,
                fileSize = metadata.fileSize,
                shelfId = targetShelfId,
                categoryId = null,
            )
            successCount++
        }
        return successCount
    }
}
