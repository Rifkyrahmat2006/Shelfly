package com.shelfly.app.feature.material

import android.content.Intent
import android.net.Uri

// PIC: Person C — Open Material via intent eksternal (PRD section 25).
fun buildOpenMaterialIntent(uri: Uri, fileType: String): Intent =
    Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, fileType)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }