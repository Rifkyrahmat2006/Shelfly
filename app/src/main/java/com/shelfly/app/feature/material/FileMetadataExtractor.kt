package com.shelfly.app.feature.material

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

// PIC: Person C — Metadata dari URI (PRD section 18). Dipanggil setelah user
// pilih file via system picker (C1), sebelum insert ke MaterialRepository.
data class FileMetadata(val title: String, val fileType: String, val fileSize: Long)

fun extractFileMetadata(context: Context, uri: Uri): FileMetadata {
    var title = "unknown"
    var size = 0L
    context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        val nameIdx = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        val sizeIdx = cursor.getColumnIndex(OpenableColumns.SIZE)
        if (cursor.moveToFirst()) {
            if (nameIdx >= 0) title = cursor.getString(nameIdx)
            if (sizeIdx >= 0) size = cursor.getLong(sizeIdx)
        }
    }
    val fileType = context.contentResolver.getType(uri) ?: "application/octet-stream"
    return FileMetadata(title = title, fileType = fileType, fileSize = size)
}