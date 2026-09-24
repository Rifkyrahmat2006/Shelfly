package com.shelfly.app.feature.shelf

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// PIC: Person B — Shelf detail + material list (PRD section 17, 18)
@Composable
fun ShelfDetailScreen(
    shelfId: Long,
    onOpenMaterial: (Long) -> Unit,
) {
    Scaffold { padding ->
        Column(modifier = Modifier.fillMaxSize()) {
            Text("Shelf #$shelfId")
            // TODO Person B: list material dari MaterialRepository.getByShelf(shelfId)
            // TODO Person C: tombol "Import Material" -> file picker
        }
    }
}
