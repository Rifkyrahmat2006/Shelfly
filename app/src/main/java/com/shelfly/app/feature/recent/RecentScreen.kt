package com.shelfly.app.feature.recent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.shelfly.app.core.component.MaterialCard
import com.shelfly.app.data.local.ShelflyDatabase
import com.shelfly.app.data.repository.MaterialRepository
import com.shelfly.app.feature.state.UiState
import kotlinx.coroutines.launch

// PIC: Person A - Recently Opened (PRD section 23)
@Composable
fun RecentScreen(onOpenMaterial: (Long) -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val repository = remember {
        MaterialRepository(ShelflyDatabase.getInstance(context).materialDao())
    }
    val viewModel = remember { RecentViewModel(repository) }
    val state by viewModel.recent().collectAsState(initial = UiState.Loading)

    Scaffold { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Text("Recent", style = MaterialTheme.typography.headlineLarge)
            when (val s = state) {
                is UiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is UiState.Empty -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Belum ada Material yang dibuka.")
                    }
                }
                is UiState.Success -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(s.data) { material ->
                            MaterialCard(
                                title = material.title,
                                subtitle = material.fileType,
                                isFavorite = material.isFavorite,
                                onClick = {
                                    scope.launch { viewModel.markOpened(material) }
                                    onOpenMaterial(material.id)
                                },
                                onToggleFavorite = {
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
                                onDelete = {
                                    scope.launch { repository.delete(material) }
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
