package com.shelfly.app.feature.shelves

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shelfly.app.core.component.ShelfCard
import com.shelfly.app.data.local.ShelflyDatabase
import com.shelfly.app.data.repository.ShelfRepository
import com.shelfly.app.feature.state.UiState
import androidx.compose.ui.platform.LocalContext

// PIC: Person A - All Shelves list (PRD section 41 pt 2)
@Composable
fun ShelvesScreen(onOpenShelf: (Long) -> Unit, onCreateShelf: () -> Unit = {}) {
    val context = LocalContext.current
    val viewModel = remember {
        ShelvesViewModel(ShelfRepository(ShelflyDatabase.getInstance(context).shelfDao()))
    }
    val state by viewModel.observeShelves().collectAsState(initial = UiState.Loading)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateShelf) {
                Icon(Icons.Filled.Add, contentDescription = "Create Shelf")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Text("Shelves", style = MaterialTheme.typography.headlineLarge)
            when (val s = state) {
                is UiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is UiState.Empty -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Belum ada Shelf. Ketuk + untuk membuat.")
                    }
                }
                is UiState.Success -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(s.data) { shelfWithCount ->
                            ShelfCard(
                                name = shelfWithCount.shelf.name,
                                materialCount = shelfWithCount.materialCount,
                                onClick = { onOpenShelf(shelfWithCount.shelf.id) },
                            )
                        }
                    }
                }
                is UiState.Error -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(s.message)
                    }
                }
            }
        }
    }
}
