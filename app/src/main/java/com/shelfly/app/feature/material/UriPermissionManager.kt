package com.shelfly.app.feature.material

import android.content.Context
import android.content.Intent
import android.net.Uri

// PIC: Person C — Persist URI permission (PRD section 33, 34) supaya
// file tetap bisa diakses setelah app restart. Hanya URI dari
// ACTION_OPEN_DOCUMENT yang punya flag persistable; URI lain (misal dari
// FileProvider internal) akan throw SecurityException — di-catch, return false,
// bukan crash app.
fun persistUriPermission(context: Context, uri: Uri): Boolean = try {
    context.contentResolver.takePersistableUriPermission(
        uri,
        Intent.FLAG_GRANT_READ_URI_PERMISSION
    )
    true
} catch (e: SecurityException) {
    false
}