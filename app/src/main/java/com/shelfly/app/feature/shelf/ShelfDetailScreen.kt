package com.shelfly.app.feature.shelf

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DriveFileMove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import com.shelfly.app.R
import com.shelfly.app.core.component.ConfirmationDialog
import com.shelfly.app.core.component.MaterialCard
import com.shelfly.app.data.local.ShelflyDatabase
import com.shelfly.app.data.local.entity.MaterialEntity
import com.shelfly.app.data.local.entity.ShelfEntity
import com.shelfly.app.data.repository.MaterialRepository
import com.shelfly.app.data.repository.ShelfRepository
import com.shelfly.app.feature.state.UiState
import kotlinx.coroutines.launch

// PIC: Person B — Shelf detail + material list (PRD section 17, 18)
// PIC: Person C — tombol "Import Material" -> onAddMaterial (file picker ada di AddMaterialScreen)
// Multi-select (user request): long-press MaterialCard masuk selection mode, TopAppBar
// berubah jadi bulk actions bar (hapus/pindah Shelf) saat ada item terpilih.
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ShelfDetailScreen(
    shelfId: Long,
    onOpenMaterial: (Long) -> Unit,
    onAddMaterial: (Long) -> Unit = {},
    onBack: () -> Unit = {},
    onDeleted: () -> Unit = {},
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val viewModel = remember {
        val db = ShelflyDatabase.getInstance(context)
        ShelfDetailViewModel(ShelfRepository(db.shelfDao()), MaterialRepository(db.materialDao()))
    }
    var shelfName by remember { mutableStateOf("Shelf #$shelfId") }
    var shelfEntity by remember { mutableStateOf<ShelfEntity?>(null) }
    var showDeleteShelfDialog by remember { mutableStateOf(false) }
    var materialToDelete by remember { mutableStateOf<MaterialEntity?>(null) }
    val state by viewModel.observeMaterials(shelfId).collectAsState(initial = UiState.Loading)
    val allShelves by viewModel.observeAllShelves().collectAsState(initial = emptyList())

    var selectedIds by remember { mutableStateOf<Set<Long>>(emptySet()) }
    val selectionMode = selectedIds.isNotEmpty()
    var showBulkDeleteDialog by remember { mutableStateOf(false) }
    var showMoveDialog by remember { mutableStateOf(false) }

    LaunchedEffect(shelfId) {
        viewModel.loadShelf(shelfId)?.let {
            shelfName = it.name
            shelfEntity = it
        }
    }

    Scaffold(
        floatingActionButton = {
            if (state !is UiState.Empty && !selectionMode) {
                FloatingActionButton(onClick = { onAddMaterial(shelfId) }) {
                    Icon(Icons.Filled.Add, contentDescription = "Import Material")
                }
            }
        },
        topBar = {
            if (selectionMode) {
                TopAppBar(
                    title = { Text("${selectedIds.size} dipilih") },
                    navigationIcon = {
                        IconButton(onClick = { selectedIds = emptySet() }) {
                            Icon(Icons.Filled.Close, contentDescription = "Batal pilih")
                        }
                    },
                    actions = {
                        IconButton(onClick = { showMoveDialog = true }) {
                            Icon(Icons.Filled.DriveFileMove, contentDescription = "Pindah ke Shelf")
                        }
                        IconButton(onClick = { showBulkDeleteDialog = true }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Hapus")
                        }
                    },
                )
            } else {
                TopAppBar(
                    title = { Text(shelfName) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        IconButton(onClick = { showDeleteShelfDialog = true }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Delete Shelf")
                        }
                    },
                )
            }
        },
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
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
                                painter = painterResource(id = R.drawable.illustration_shelf_empty),
                                contentDescription = null,
                                modifier = Modifier.height(160.dp),
                            )
                            Text("Belum ada Material di Shelf ini.")
                            Button(onClick = { onAddMaterial(shelfId) }) {
                                Text("Import Material")
                            }
                        }
                    }
                }
                is UiState.Success -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(s.data) { material ->
                            MaterialCard(
                                title = material.title,
                                subtitle = material.fileType,
                                isFavorite = material.isFavorite,
                                selectionMode = selectionMode,
                                isSelected = selectedIds.contains(material.id),
                                onClick = { onOpenMaterial(material.id) },
                                onLongClick = {
                                    selectedIds = selectedIds + material.id
                                },
                                onToggleSelect = {
                                    selectedIds = if (selectedIds.contains(material.id)) {
                                        selectedIds - material.id
                                    } else {
                                        selectedIds + material.id
                                    }
                                },
                                onToggleFavorite = {
                                    val repository = MaterialRepository(
                                        ShelflyDatabase.getInstance(context).materialDao()
                                    )
                                    scope.launch { repository.toggleFavorite(material) }
                                },
                                onShare = {
                                    context.startActivity(
                                        com.shelfly.app.feature.material.buildShareMaterialIntent(
                                            android.net.Uri.parse(material.fileUri),
                                            material.fileType,
                                        ),
                                    )
                                },
                                onDelete = { materialToDelete = material },
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

    if (showDeleteShelfDialog) {
        ConfirmationDialog(
            title = "Hapus Shelf",
            message = "Shelf \"$shelfName\" dan semua Material di dalamnya akan dihapus permanen. Lanjutkan?",
            onConfirm = {
                showDeleteShelfDialog = false
                shelfEntity?.let { entity ->
                    scope.launch {
                        viewModel.deleteShelf(entity)
                        onDeleted()
                    }
                }
            },
            onDismiss = { showDeleteShelfDialog = false },
        )
    }

    materialToDelete?.let { material ->
        ConfirmationDialog(
            title = "Hapus Material",
            message = "\"${material.title}\" akan dihapus permanen. Lanjutkan?",
            onConfirm = {
                scope.launch { viewModel.deleteMaterial(material) }
                materialToDelete = null
            },
            onDismiss = { materialToDelete = null },
        )
    }

    if (showBulkDeleteDialog) {
        val currentState = state
        ConfirmationDialog(
            title = "Hapus ${selectedIds.size} Material",
            message = "${selectedIds.size} Material yang dipilih akan dihapus permanen. Lanjutkan?",
            onConfirm = {
                if (currentState is UiState.Success) {
                    val toDelete = currentState.data.filter { selectedIds.contains(it.id) }.toSet()
                    scope.launch { viewModel.deleteMaterials(toDelete) }
                }
                showBulkDeleteDialog = false
                selectedIds = emptySet()
            },
            onDismiss = { showBulkDeleteDialog = false },
        )
    }

    if (showMoveDialog) {
        AlertDialog(
            onDismissRequest = { showMoveDialog = false },
            title = { Text("Pindah ke Shelf") },
            text = {
                Column {
                    allShelves.filter { it.id != shelfId }.forEach { targetShelf ->
                        ListItem(
                            headlineContent = { Text(targetShelf.name) },
                            modifier = Modifier.clickable {
                                val ids = selectedIds
                                scope.launch { viewModel.moveMaterials(ids, targetShelf.id) }
                                showMoveDialog = false
                                selectedIds = emptySet()
                            },
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showMoveDialog = false }) { Text("Batal") }
            },
        )
    }
}
