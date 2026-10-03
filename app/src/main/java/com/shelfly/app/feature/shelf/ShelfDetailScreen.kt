package com.shelfly.app.feature.shelf

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.shelfly.app.core.component.MaterialCard
import com.shelfly.app.data.local.ShelflyDatabase
import com.shelfly.app.data.repository.MaterialRepository
import com.shelfly.app.data.repository.ShelfRepository
import com.shelfly.app.feature.state.UiState
import kotlinx.coroutines.launch

// PIC: Person B — Shelf detail + material list (PRD section 17, 18)
// PIC: Person C — tombol "Import Material" -> onAddMaterial (file picker ada di AddMaterialScreen)
@Composable
fun ShelfDetailScreen(
    shelfId: Long,
    onOpenMaterial: (Long) -> Unit,
    onAddMaterial: (Long) -> Unit = {},
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val viewModel = remember {
        val db = ShelflyDatabase.getInstance(context)
        ShelfDetailViewModel(ShelfRepository(db.shelfDao()), MaterialRepository(db.materialDao()))
    }
    var shelfName by remember { mutableStateOf("Shelf #$shelfId") }
    val state by viewModel.observeMaterials(shelfId).collectAsState(initial = UiState.Loading)

    LaunchedEffect(shelfId) {
        viewModel.loadShelf(shelfId)?.let { shelfName = it.name }
    }

    Scaffold { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(shelfName, style = MaterialTheme.typography.headlineLarge)
                Button(onClick = { onAddMaterial(shelfId) }) {
                    Text("Import Material")
                }
            }
            when (val s = state) {
                is UiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is UiState.Empty -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Belum ada Material di Shelf ini.")
                    }
                }
                is UiState.Success -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(s.data) { material ->
                            MaterialCard(
                                title = material.title,
                                subtitle = material.fileType,
                                isFavorite = material.isFavorite,
                                onClick = { onOpenMaterial(material.id) },
                                onToggleFavorite = {
                                    val repository = MaterialRepository(
                                        ShelflyDatabase.getInstance(context).materialDao()
                                    )
                                    scope.launch { repository.toggleFavorite(material) }
                                },
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
