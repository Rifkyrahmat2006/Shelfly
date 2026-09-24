package com.shelfly.app.feature.material

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// PIC: Person C — File Management: detail + open material (PRD section 24, 25)
@Composable
fun MaterialDetailScreen(materialId: Long) {
    Scaffold { padding ->
        Column(modifier = Modifier.fillMaxSize()) {
            Text("Material #$materialId")
            // TODO Person C: tampilkan metadata (title, type, size, shelf, category)
            // TODO Person C: tombol Open -> resolve URI, launch intent ACTION_VIEW
            // TODO Person C: handle file-not-found / unsupported viewer error
        }
    }
}
