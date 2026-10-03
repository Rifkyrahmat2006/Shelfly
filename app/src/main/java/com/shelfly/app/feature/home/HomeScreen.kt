package com.shelfly.app.feature.home

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shelfly.app.core.component.MaterialCard
import com.shelfly.app.core.component.ShelfCard
import com.shelfly.app.core.theme.Spacing

// PIC: Person A - Home (My Shelves, Search entry, Recent) - PRD section 41
// Data dummy di bawah: placeholder layout. Person D (Yunan) sambungkan ViewModel + state asli
// (Loading/Success/Empty/Error) menggantikan dummyShelves/dummyRecent ini.
data class HomeShelfUi(val id: Long, val name: String, val materialCount: Int)
data class HomeMaterialUi(val id: Long, val title: String, val subtitle: String, val isFavorite: Boolean)

private val dummyShelves = listOf(
    HomeShelfUi(1, "Pemrograman Mobile", 24),
    HomeShelfUi(2, "Basis Data", 16),
)
private val dummyRecent = listOf(
    HomeMaterialUi(1, "Materi Database Room.pdf", "Learning . PDF . 2.4 MB", false),
    HomeMaterialUi(2, "Android Navigation Compose.pdf", "Mobile Dev . PDF . 1.8 MB", true),
)

@Composable
fun HomeScreen(
    onOpenShelf: (Long) -> Unit,
    onOpenSearch: () -> Unit,
    onOpenMaterial: (Long) -> Unit = {},
    onAddMaterial: () -> Unit = {},
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddMaterial) {
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
                    modifier = Modifier.fillMaxWidth().padding(top = Spacing.sm),
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
                LazyRow(horizontalArrangement = Arrangement.spacedBy(Spacing.md)) {
                    items(dummyShelves) { shelf ->
                        Box(modifier = Modifier.padding(bottom = Spacing.xs)) {
                            ShelfCard(
                                name = shelf.name,
                                materialCount = shelf.materialCount,
                                onClick = { onOpenShelf(shelf.id) },
                            )
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
            items(dummyRecent) { material ->
                MaterialCard(
                    title = material.title,
                    subtitle = material.subtitle,
                    isFavorite = material.isFavorite,
                    onClick = { onOpenMaterial(material.id) },
                )
            }
        }
    }
}
