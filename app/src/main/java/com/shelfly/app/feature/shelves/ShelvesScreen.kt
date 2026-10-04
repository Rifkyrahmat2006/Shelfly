package com.shelfly.app.feature.shelves

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.shelfly.app.R
import com.shelfly.app.core.component.ConfirmationDialog
import com.shelfly.app.core.component.ShelfCard
import com.shelfly.app.data.local.ShelflyDatabase
import com.shelfly.app.data.local.entity.ShelfEntity
import com.shelfly.app.data.repository.ShelfRepository
import com.shelfly.app.feature.state.UiState
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch

// PIC: Person A - All Shelves list (PRD section 41 pt 2)
@Composable
fun ShelvesScreen(onOpenShelf: (Long) -> Unit, onCreateShelf: () -> Unit = {}) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val viewModel = remember {
        ShelvesViewModel(ShelfRepository(ShelflyDatabase.getInstance(context).shelfDao()))
    }
    var shelfToDelete by remember { mutableStateOf<ShelfEntity?>(null) }
    val state by viewModel.observeShelves().collectAsState(initial = UiState.Loading)

    Scaffold(
        floatingActionButton = {
            if (state !is UiState.Empty) {
                FloatingActionButton(onClick = onCreateShelf) {
                    Icon(Icons.Filled.Add, contentDescription = "Create Shelf")
                }
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
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.illustration_shelves_empty),
                                contentDescription = null,
                                modifier = Modifier.height(160.dp),
                            )
                            Text("Belum ada Shelf.")
                            Button(onClick = onCreateShelf) {
                                Text("Create Shelf")
                            }
                        }
                    }
                }
                is UiState.Success -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(s.data) { shelfWithCount ->
                            ShelfCard(
                                name = shelfWithCount.shelf.name,
                                materialCount = shelfWithCount.materialCount,
                                onClick = { onOpenShelf(shelfWithCount.shelf.id) },
                                onDelete = { shelfToDelete = shelfWithCount.shelf },
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

    shelfToDelete?.let { shelf ->
        ConfirmationDialog(
            title = "Hapus Shelf",
            message = "Shelf \"${shelf.name}\" dan semua Material di dalamnya akan dihapus permanen. Lanjutkan?",
            onConfirm = {
                scope.launch { viewModel.deleteShelf(shelf) }
                shelfToDelete = null
            },
            onDismiss = { shelfToDelete = null },
        )
    }
}
