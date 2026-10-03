package com.shelfly.app.feature.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// PIC: Person A - Favorites (PRD section 22)
@Composable
fun FavoritesScreen(onOpenMaterial: (Long) -> Unit) {
    Scaffold { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Text("Favorites", style = MaterialTheme.typography.headlineLarge)
            // TODO Person D: sambungkan query isFavorite = true, render pakai MaterialCard
        }
    }
}
