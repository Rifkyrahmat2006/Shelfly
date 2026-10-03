package com.shelfly.app.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold as M3Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shelfly.app.feature.favorites.FavoritesScreen
import com.shelfly.app.feature.home.HomeScreen
import com.shelfly.app.feature.material.AddMaterialScreen
import com.shelfly.app.feature.material.AddMaterialViewModel
import com.shelfly.app.feature.material.MaterialDetailScreen
import com.shelfly.app.feature.recent.RecentScreen
import com.shelfly.app.feature.search.SearchScreen
import com.shelfly.app.feature.shelf.ShelfDetailScreen
import com.shelfly.app.feature.shelf.ShelfFormScreen
import com.shelfly.app.feature.shelf.ShelfFormViewModel
import com.shelfly.app.feature.shelves.ShelvesScreen
import com.shelfly.app.data.local.ShelflyDatabase
import com.shelfly.app.data.repository.MaterialRepository
import com.shelfly.app.data.repository.ShelfRepository
import androidx.compose.ui.platform.LocalContext

// PIC: Person A - Navigation graph (PRD section 42, 43)
object Routes {
    const val HOME = "home"
    const val SHELVES = "shelves"
    const val RECENT = "recent"
    const val FAVORITES = "favorites"
    const val SHELF_DETAIL = "shelf/{shelfId}"
    const val MATERIAL_DETAIL = "material/{materialId}"
    const val SEARCH = "search"
    const val ADD_MATERIAL = "add-material/{shelfId}"
    const val SHELF_FORM_CREATE = "shelf-form"
    const val SHELF_FORM_EDIT = "shelf-form/{shelfId}"

    fun shelfDetail(shelfId: Long) = "shelf/$shelfId"
    fun materialDetail(materialId: Long) = "material/$materialId"
    fun addMaterial(shelfId: Long) = "add-material/$shelfId"
    fun shelfFormEdit(shelfId: Long) = "shelf-form/$shelfId"
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
                    onAddMaterial = { shelfId -> navController.navigate(Routes.addMaterial(shelfId)) },
                )
            }
            composable(Routes.SHELVES) {
                ShelvesScreen(
                    onOpenShelf = { id -> navController.navigate(Routes.shelfDetail(id)) },
                    onCreateShelf = { navController.navigate(Routes.SHELF_FORM_CREATE) },
                )
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
                    onAddMaterial = { sid -> navController.navigate(Routes.addMaterial(sid)) },
                    onBack = { navController.popBackStack() },
                    onDeleted = { navController.popBackStack() },
                )
            }
            composable(Routes.MATERIAL_DETAIL) { backStackEntry ->
                val materialId = backStackEntry.arguments?.getString("materialId")?.toLongOrNull() ?: 0L
                MaterialDetailScreen(
                    materialId = materialId,
                    onBack = { navController.popBackStack() },
                    onDeleted = { navController.popBackStack() },
                )
            }
            composable(Routes.SEARCH) {
                SearchScreen(
                    onOpenMaterial = { id -> navController.navigate(Routes.materialDetail(id)) },
                    onBack = { navController.popBackStack() },
                )
            }
            composable(Routes.ADD_MATERIAL) { backStackEntry ->
                val shelfId = backStackEntry.arguments?.getString("shelfId")?.toLongOrNull() ?: 0L
                val context = LocalContext.current
                val repository = remember { MaterialRepository(ShelflyDatabase.getInstance(context).materialDao()) }
                val viewModel = remember { AddMaterialViewModel(repository, context) }
                AddMaterialScreen(
                    viewModel = viewModel,
                    shelfId = shelfId,
                    onSaved = { navController.popBackStack() },
                    onBack = { navController.popBackStack() },
                )
            }
            composable(Routes.SHELF_FORM_CREATE) {
                val context = LocalContext.current
                val repository = remember { ShelfRepository(ShelflyDatabase.getInstance(context).shelfDao()) }
                val viewModel = remember { ShelfFormViewModel(repository, existingShelfId = null) }
                ShelfFormScreen(
                    viewModel = viewModel,
                    isEditMode = false,
                    onSaved = { navController.popBackStack() },
                    onBack = { navController.popBackStack() },
                )
            }
            composable(Routes.SHELF_FORM_EDIT) { backStackEntry ->
                val shelfId = backStackEntry.arguments?.getString("shelfId")?.toLongOrNull() ?: 0L
                val context = LocalContext.current
                val repository = remember { ShelfRepository(ShelflyDatabase.getInstance(context).shelfDao()) }
                val viewModel = remember { ShelfFormViewModel(repository, existingShelfId = shelfId) }
                ShelfFormScreen(
                    viewModel = viewModel,
                    isEditMode = true,
                    onSaved = { navController.popBackStack() },
                    onBack = { navController.popBackStack() },
                )
            }
        }
    }
}
