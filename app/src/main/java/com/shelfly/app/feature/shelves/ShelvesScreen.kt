package com.shelfly.app.feature.shelves

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// PIC: Person A - All Shelves list (PRD section 41 pt 2)
@Composable
fun ShelvesScreen(onOpenShelf: (Long) -> Unit, onCreateShelf: () -> Unit = {}) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateShelf) {
                Icon(Icons.Filled.Add, contentDescription = "Create Shelf")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Text("Shelves", style = MaterialTheme.typography.headlineLarge)
            // TODO Person B: sambungkan ShelfRepository.getAllShelves(), render pakai ShelfCard
        }
    }
}
