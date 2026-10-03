package com.shelfly.app.core.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// PIC: Person A - Bottom Navigation (PRD section 43)
enum class BottomNavDestination(val route: String, val label: String) {
    HOME(Routes.HOME, "Home"),
    SHELVES(Routes.SHELVES, "Shelves"),
    RECENT(Routes.RECENT, "Recent"),
    FAVORITES(Routes.FAVORITES, "Favorites"),
}

@Composable
fun ShelflyBottomNavBar(
    currentRoute: String?,
    onNavigate: (BottomNavDestination) -> Unit,
) {
    NavigationBar(modifier = Modifier.height(64.dp)) {
        BottomNavDestination.entries.forEach { destination ->
            NavigationBarItem(
                selected = currentRoute == destination.route,
                onClick = { onNavigate(destination) },
                icon = {
                    val icon = when (destination) {
                        BottomNavDestination.HOME -> Icons.Filled.Home
                        BottomNavDestination.SHELVES -> Icons.Filled.LibraryBooks
                        BottomNavDestination.RECENT -> Icons.Filled.Schedule
                        BottomNavDestination.FAVORITES -> Icons.Filled.Favorite
                    }
                    Icon(icon, contentDescription = destination.label)
                },
                label = { Text(destination.label) },
            )
        }
    }
}
