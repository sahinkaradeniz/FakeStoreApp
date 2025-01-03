package com.skapps.fakestoreapp.navigation

object HomeDestination {
    const val route = "home"
    // istersen val deepLink = ...
}

object DetailDestination {
    private const val baseRoute = "detail"
    const val productIdArg = "productId"

    // final route -> detail/{productId}
    const val route = "$baseRoute/{$productIdArg}"

    // Kullanışlı bir fonksiyon: parametreli route üretimi
    fun createRoute(productId: String): String {
        return "$baseRoute/$productId"
    }
}