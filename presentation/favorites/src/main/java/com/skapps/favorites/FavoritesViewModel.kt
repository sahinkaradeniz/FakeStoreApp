package com.skapps.favorites

import androidx.lifecycle.viewModelScope
import com.skapps.fakestoreapp.core.base.BaseViewModel
import com.skapps.fakestoreapp.core.loading.GlobalLoadingManager
import com.skapps.fakestoreapp.domain.entitiy.favorites.FavoritesEntity
import com.skapps.fakestoreapp.domain.onError
import com.skapps.fakestoreapp.domain.onErrorWithMessage
import com.skapps.fakestoreapp.domain.onSuccess
import com.skapps.fakestoreapp.domain.usecase.favorites.delete.DeleteProductToFavoritesUseCase
import com.skapps.fakestoreapp.domain.usecase.favorites.getAll.GetAllFavoriteProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    globalLoadingManager: GlobalLoadingManager,
    private val getAllFavoriteProductsUseCase: GetAllFavoriteProductsUseCase,
    private val deleteProductToFavoritesUseCase: DeleteProductToFavoritesUseCase
) : BaseViewModel<FavoritesUiState, FavoritesUiAction, FavoritesSideEffect>(
    initialState = FavoritesUiState.EMPTY,
    globalLoadingManager = globalLoadingManager
) {

    init {
        onAction(FavoritesUiAction.LoadFavorites)
    }


    override fun onAction(uiAction: FavoritesUiAction) {
        when (uiAction) {
            is FavoritesUiAction.FavoriteButtonClicked -> {
                deleteProductToFavorites(uiAction.id)
            }

            is FavoritesUiAction.LoadFavorites -> {
                getAllFavoriteProducts()
            }

            is FavoritesUiAction.ProductClicked -> {

            }
        }
    }

    private fun getAllFavoriteProducts() {
        viewModelScope.launch {
            getAllFavoriteProductsUseCase.invoke().distinctUntilChanged().catch {
                emitSideEffect(FavoritesSideEffect.ShowErrorGetFavorites(it.message ?: "Error"))
            }.collectLatest {
                updateUiState {
                    copy(
                        favorites = it.map { it.toFavoriteUiModel() },
                        isEmpty = it.isEmpty()
                    )
                }
            }
        }
    }

    private fun deleteProductToFavorites(id: String) {
        viewModelScope.launch {
           doWithGlobalLoading {
                deleteProductToFavoritesUseCase.invoke(id).onErrorWithMessage {
                    emitSideEffect(FavoritesSideEffect.ShowErrorDeleteFavorites(it))
                }.onSuccess {
                    emitSideEffect(FavoritesSideEffect.ShowSuccessDeleteFavorites)
                }
           }
        }
    }


    private fun FavoritesEntity.toFavoriteUiModel(): FavoriteUiModel {
        return FavoriteUiModel(
            id = id,
            title = title,
            description = description,
            thumbnail = images,
            price = newPrice,
            isFavorite = true
        )
    }
}