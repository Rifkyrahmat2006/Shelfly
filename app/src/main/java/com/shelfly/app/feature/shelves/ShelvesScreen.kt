package com.shelfly.app.feature.shelves

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.shelfly.app.core.component.CreateEditShelfSheet
import com.shelfly.app.core.theme.Spacing

// PIC: Person A (Nadine) - All Shelves list (PRD section 41 pt 2)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShelvesScreen(
    onOpenShelf: (Long) -> Unit,
    onCreateShelf: (name: String, description: String?, icon: String?) -> Unit = { _, _, _ -> },
) {
    var showCreateSheet by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showCreateSheet = true }) {
                Icon(Icons.Filled.Add, contentDescription = "Create Shelf")
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(Spacing.lg),
        ) {
            Text("Shelves", style = MaterialTheme.typography.headlineLarge)
            // TODO Person B: sambungkan ShelfRepository.getAllShelves(), render pakai ShelfCard
        }

        if (showCreateSheet) {
            CreateEditShelfSheet(
                onDismiss = { showCreateSheet = false },
                onSave = { name, desc, icon ->
                    onCreateShelf(name, desc, icon)
                    showCreateSheet = false
                },
            )
        }
    }
}
