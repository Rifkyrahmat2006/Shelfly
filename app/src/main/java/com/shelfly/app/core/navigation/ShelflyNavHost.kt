package com.shelfly.app.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shelfly.app.feature.home.HomeScreen
import com.shelfly.app.feature.material.MaterialDetailScreen
import com.shelfly.app.feature.search.SearchScreen
import com.shelfly.app.feature.shelf.ShelfDetailScreen

// PIC: Person A — Navigation graph (PRD section 42)
object Routes {
    const val HOME = "home"
    const val SHELF_DETAIL = "shelf/{shelfId}"
    const val MATERIAL_DETAIL = "material/{materialId}"
    const val SEARCH = "search"

    fun shelfDetail(shelfId: Long) = "shelf/$shelfId"
    fun materialDetail(materialId: Long) = "material/$materialId"
}

@Composable
fun ShelflyNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(
                onOpenShelf = { id -> navController.navigate(Routes.shelfDetail(id)) },
                onOpenSearch = { navController.navigate(Routes.SEARCH) },
            )
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
