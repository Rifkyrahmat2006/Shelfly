package com.shelfly.app.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold as M3Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shelfly.app.feature.favorites.FavoritesScreen
import com.shelfly.app.feature.home.HomeScreen
import com.shelfly.app.feature.material.MaterialDetailScreen
import com.shelfly.app.feature.recent.RecentScreen
import com.shelfly.app.feature.search.SearchScreen
import com.shelfly.app.feature.shelf.ShelfDetailScreen
import com.shelfly.app.feature.shelves.ShelvesScreen

// PIC: Person A - Navigation graph (PRD section 42, 43)
object Routes {
    const val HOME = "home"
    const val SHELVES = "shelves"
    const val RECENT = "recent"
    const val FAVORITES = "favorites"
    const val SHELF_DETAIL = "shelf/{shelfId}"
    const val MATERIAL_DETAIL = "material/{materialId}"
    const val SEARCH = "search"

    fun shelfDetail(shelfId: Long) = "shelf/$shelfId"
    fun materialDetail(materialId: Long) = "material/$materialId"
}

private val BOTTOM_NAV_ROUTES = setOf(Routes.HOME, Routes.SHELVES, Routes.RECENT, Routes.FAVORITES)

@Composable
fun ShelflyNavHost(navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    M3Scaffold(
        bottomBar = {
            if (currentRoute in BOTTOM_NAV_ROUTES) {
                ShelflyBottomNavBar(
                    currentRoute = currentRoute,
                    onNavigate = { destination ->
                        navController.navigate(destination.route) {
                            popUpTo(Routes.HOME) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
        },
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = androidx.compose.ui.Modifier.padding(padding),
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    onOpenShelf = { id -> navController.navigate(Routes.shelfDetail(id)) },
                    onOpenSearch = { navController.navigate(Routes.SEARCH) },
                    onOpenMaterial = { id -> navController.navigate(Routes.materialDetail(id)) },
                )
            }
            composable(Routes.SHELVES) {
                ShelvesScreen(onOpenShelf = { id -> navController.navigate(Routes.shelfDetail(id)) })
            }
            composable(Routes.RECENT) {
                RecentScreen(onOpenMaterial = { id -> navController.navigate(Routes.materialDetail(id)) })
            }
            composable(Routes.FAVORITES) {
                FavoritesScreen(onOpenMaterial = { id -> navController.navigate(Routes.materialDetail(id)) })
            }
            composable(Routes.SHELF_DETAIL) { backStackEntry ->
                val shelfId = backStackEntry.arguments?.getString("shelfId")?.toLongOrNull() ?: 0L
                ShelfDetailScreen(
                    shelfId = shelfId,
                    onOpenMaterial = { id -> navController.navigate(Routes.materialDetail(id)) },
                )
            }
            composable(Routes.MATERIAL_DETAIL) { backStackEntry ->
                val materialId = backStackEntry.arguments?.getString("materialId")?.toLongOrNull() ?: 0L
                MaterialDetailScreen(materialId = materialId)
            }
            composable(Routes.SEARCH) {
                SearchScreen(onOpenMaterial = { id -> navController.navigate(Routes.materialDetail(id)) })
            }
        }
    }
}
