package com.shelfly.app.feature.material

import android.content.ActivityNotFoundException
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import android.net.Uri

// PIC: Person C — File Management: detail + open material (PRD section 24, 25)
// fileUri/fileType di-pass dari caller (nanti NavHost ambil dari MaterialRepository
// via materialId) — MaterialDetailScreen sendiri tidak depend ke Room, tetap stateless.
@Composable
fun MaterialDetailScreen(
    materialId: Long,
    fileUri: String? = null,
    fileType: String? = null,
) {
    val context = LocalContext.current
    var openError by remember { mutableStateOf<String?>(null) }

    Scaffold { padding ->
        Column(modifier = Modifier.fillMaxSize()) {
            Text("Material #$materialId")
            if (fileUri != null && fileType != null) {
                Button(onClick = {
                    try {
                        val intent = buildOpenMaterialIntent(Uri.parse(fileUri), fileType)
                        context.startActivity(intent)
                        openError = null
                    } catch (e: ActivityNotFoundException) {
                        openError = mapMaterialError(e)
                    }
                }) {
                    Text("Open")
                }
            }
            if (openError != null) {
                Text(openError!!)
            }
        }
    }
}
