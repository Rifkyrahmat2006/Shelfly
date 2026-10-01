package com.shelfly.app.feature.recent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// PIC: Person A - Recently Opened (PRD section 23)
@Composable
fun RecentScreen(onOpenMaterial: (Long) -> Unit) {
    Scaffold { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Text("Recent", style = MaterialTheme.typography.headlineLarge)
            // TODO Person D: sambungkan query lastOpenedAt DESC, render pakai MaterialCard
        }
    }
}
