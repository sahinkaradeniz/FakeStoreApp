package com.skapps.fakestoreapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = HomeDestination.route
) {
    NavHost(navController = navController, startDestination = startDestination) {

        // 1) Home
        homeScreen(
            onNavigateToDetail = { productId ->

                navController.navigate(DetailDestination.createRoute(productId))
            }
        )

        // 2) Detail
        detailScreen()

        // 3) İstersen başka feature nav graph fonksiyonları:
        // profileScreen(...)
        // cartScreen(...)
    }
}