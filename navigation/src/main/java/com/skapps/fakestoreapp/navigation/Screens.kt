package com.skapps.fakestoreapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.skapps.detail.ProductDetailScreen
import com.skapps.home.HomeScreen

fun NavGraphBuilder.homeScreen(
    onNavigateToDetail: (String) -> Unit
) {
    composable(route = HomeDestination.route) {
        HomeScreen(
            onNavigateToDetail = onNavigateToDetail
        )
    }
}

fun NavGraphBuilder.detailScreen() {
    composable(
        route = DetailDestination.route,
        arguments = listOf(
            navArgument(DetailDestination.productIdArg) {
                type = NavType.StringType
                nullable = false
            }
        )
    ) { backStackEntry ->
        val productId = backStackEntry.arguments?.getString(DetailDestination.productIdArg)
        ProductDetailScreen(productId = productId ?: "")
    }
}