package com.shelfly.app.feature.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// PIC: Person D — Search, Filter, Sort, Favorite, Recent (PRD section 19-23)
@Composable
fun SearchScreen(onOpenMaterial: (Long) -> Unit) {
    Scaffold { padding ->
        Column(modifier = Modifier.fillMaxSize()) {
            Text("Search")
            // TODO Person D: search bar -> MaterialRepository.search(query)
            // TODO Person D: filter chips (fileType, category, favorite)
            // TODO Person D: sort options (name, date, recent)
        }
    }
}
