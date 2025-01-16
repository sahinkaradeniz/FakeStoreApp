package com.skapps.favorites


data class FavoritesUiState(
    val favorites: List<FavoriteUiModel>,
    val isEmpty: Boolean = favorites.isEmpty()
){
    companion object {
        val EMPTY by lazy { FavoritesUiState(emptyList()) }
    }
}


sealed interface FavoritesUiAction {
    data object LoadFavorites : FavoritesUiAction
    data class ProductClicked(val id: String) : FavoritesUiAction
    data class FavoriteButtonClicked(val loadingMessage: String,val id:String) : FavoritesUiAction
}

sealed interface FavoritesSideEffect {
    data class ShowErrorGetFavorites(val error: String) : FavoritesSideEffect
    data class ShowErrorDeleteFavorites(val error: String) : FavoritesSideEffect
    data object ShowSuccessDeleteFavorites : FavoritesSideEffect
    data class NavigateToProductDetail(val id: String) : FavoritesSideEffect
}

data class FavoriteUiModel(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnail: String,
    val price: Double,
    val isFavorite: Boolean
)
