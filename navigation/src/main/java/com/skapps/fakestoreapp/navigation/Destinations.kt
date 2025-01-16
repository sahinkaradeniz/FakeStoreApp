package com.skapps.fakestoreapp.navigation

object HomeDestination {
    const val route = "home"
}

object DetailDestination {
    private const val baseRoute = "detail"
    const val productIdArg = "productId"

    const val route = "$baseRoute/{$productIdArg}"

    fun createRoute(productId: String): String {
        return "$baseRoute/$productId"
    }
}

object BasketDestination {
    const val route = "basket"
}

object FavoriteDestination {
    const val route = "favorite"
}

sealed class BottomNavScreen(val route: String) {
    object Home : BottomNavScreen(HomeDestination.route)
    object Basket : BottomNavScreen(BasketDestination.route)
    object Favorite : BottomNavScreen(FavoriteDestination.route)
}