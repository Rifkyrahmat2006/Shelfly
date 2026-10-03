package com.shelfly.app.feature.material

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import java.io.File

// PIC: Person C — In-app PDF viewer (render halaman jadi Bitmap via PdfRenderer
// native Android, tidak perlu library eksternal). Satu renderer per file dibuka,
// harus close() setelah selesai (idealnya di DisposableEffect/onDispose Compose).
class PdfPageRenderer private constructor(
    private val fileDescriptor: ParcelFileDescriptor,
) {
    private val renderer = PdfRenderer(fileDescriptor)

    companion object {
        fun fromFile(file: File): PdfPageRenderer =
            PdfPageRenderer(ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY))

        fun fromUri(context: Context, uri: Uri): PdfPageRenderer {
            val pfd = context.contentResolver.openFileDescriptor(uri, "r")
                ?: throw IllegalArgumentException("Tidak bisa membuka URI: $uri")
            return PdfPageRenderer(pfd)
        }
    }

    val pageCount: Int get() = renderer.pageCount

    fun renderPage(index: Int): Bitmap {
        if (index < 0 || index >= pageCount) {
            throw IndexOutOfBoundsException("Page $index tidak valid, total halaman: $pageCount")
        }
        renderer.openPage(index).use { page ->
            val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            return bitmap
        }
    }

    fun close() {
        renderer.close()
        fileDescriptor.close()
    }
}