package com.shelfly.app.feature.material

import android.content.Intent
import android.net.Uri

// PIC: Person C — Open Material via intent eksternal (PRD section 25).
fun buildOpenMaterialIntent(uri: Uri, fileType: String): Intent =
    Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, fileType)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

// PIC: Person C — Share Material via intent eksternal (user request: bagikan
// material). Pakai ACTION_SEND + chooser, grant read permission sama seperti
// Open supaya app penerima (WhatsApp, Gmail, dll) bisa akses content:// URI.
fun buildShareMaterialIntent(uri: Uri, fileType: String): Intent {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        type = fileType
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    return Intent.createChooser(sendIntent, "Bagikan Material")
}