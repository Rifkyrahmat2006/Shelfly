package com.shelfly.app.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// PIC: Person A — Home (My Shelves, Search entry, Recent, Favorites) — PRD section 41
@Composable
fun HomeScreen(
    onOpenShelf: (Long) -> Unit,
    onOpenSearch: () -> Unit,
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
        ) {
            Text("Shelfly", style = MaterialTheme.typography.headlineLarge)
            // TODO Person A: My Shelves list, Recent, Favorites section
            // TODO Person B: sambungkan ke ShelfRepository.getAllShelves()
        }
    }
}
