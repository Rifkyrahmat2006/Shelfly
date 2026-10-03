package com.shelfly.app.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.shelfly.app.core.component.MaterialCard
import com.shelfly.app.core.component.ShelfCard
import com.shelfly.app.core.theme.Spacing
import com.shelfly.app.data.local.ShelflyDatabase
import com.shelfly.app.data.repository.MaterialRepository
import com.shelfly.app.data.repository.ShelfRepository

// PIC: Person A - Home (My Shelves, Search entry, Recent) - PRD section 41
@Composable
fun HomeScreen(
    onOpenShelf: (Long) -> Unit,
    onOpenSearch: () -> Unit,
    onOpenMaterial: (Long) -> Unit = {},
    onAddMaterial: (Long) -> Unit = {},
    onCreateShelf: () -> Unit = {},
) {
    val context = LocalContext.current
    val viewModel = remember {
        val db = ShelflyDatabase.getInstance(context)
        HomeViewModel(ShelfRepository(db.shelfDao()), MaterialRepository(db.materialDao()))
    }
    val state by viewModel.observeHome().collectAsState(initial = HomeUiState())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val firstShelfId = state.shelves.firstOrNull()?.shelf?.id
                    if (firstShelfId != null) {
                        onAddMaterial(firstShelfId)
                    } else {
                        onCreateShelf()
                    }
                },
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Material")
            }
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(Spacing.lg),
            verticalArrangement = Arrangement.spacedBy(Spacing.lg),
        ) {
            item {
                Column(modifier = Modifier.padding(padding)) {
                    Text("Good evening,", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Halo, Alex!", style = MaterialTheme.typography.headlineLarge)
                }
            }
            item {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Search materials...") },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().padding(top = Spacing.sm)
                        .clickable(onClick = onOpenSearch),
                    enabled = false,
                )
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("My Shelves", style = MaterialTheme.typography.titleMedium)
                    Text("See all", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                }
            }
            item {
                if (state.shelves.isEmpty()) {
                    Text("Belum ada Shelf.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                } else {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(Spacing.md)) {
                        items(state.shelves) { shelfWithCount ->
                            Box(modifier = Modifier.padding(bottom = Spacing.xs)) {
                                ShelfCard(
                                    name = shelfWithCount.shelf.name,
                                    materialCount = shelfWithCount.materialCount,
                                    onClick = { onOpenShelf(shelfWithCount.shelf.id) },
                                )
                            }
                        }
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("Recently Opened", style = MaterialTheme.typography.titleMedium)
                    Text("See all", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                }
            }
            if (state.recent.isEmpty()) {
                item { Text("Belum ada Material yang dibuka.", color = MaterialTheme.colorScheme.onSurfaceVariant) }
            } else {
                items(state.recent) { material ->
                    MaterialCard(
                        title = material.title,
                        subtitle = material.fileType,
                        isFavorite = material.isFavorite,
                        onClick = { onOpenMaterial(material.id) },
                    )
                }
            }
        }
    }
}
