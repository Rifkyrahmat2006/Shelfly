package com.shelfly.app.feature.material

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import java.io.File

// PIC: Person C — In-app PDF viewer (render halaman jadi Bitmap via PdfRenderer
// native Android, tidak perlu library eksternal). Satu renderer per file dibuka,
// harus close() setelah selesai (idealnya di DisposableEffect/onDispose Compose).
//
// RENDER_SCALE: page.width/height dari PdfRenderer adalah ukuran asli PDF dalam
// points (72dpi, biasanya ~612x792 untuk A4) — jauh lebih kecil dari resolusi
// layar device, apalagi saat di-zoom (pinch sampai 5x di ZoomableModifier).
// Render di resolusi lebih tinggi supaya hasil zoom tetap tajam, bukan pecah/blur.
class PdfPageRenderer private constructor(
    private val fileDescriptor: ParcelFileDescriptor,
) {
    private val renderer = PdfRenderer(fileDescriptor)

    companion object {
        private const val RENDER_SCALE = 3f

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
            val width = (page.width * RENDER_SCALE).toInt()
            val height = (page.height * RENDER_SCALE).toInt()
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val matrix = Matrix().apply { setScale(RENDER_SCALE, RENDER_SCALE) }
            page.render(bitmap, null, matrix, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            return bitmap
        }
    }

    fun close() {
        renderer.close()
        fileDescriptor.close()
    }
}
