package com.skapps.detail

import androidx.lifecycle.viewModelScope
import com.skapps.fakestoreapp.core.base.BaseViewModel
import com.skapps.fakestoreapp.core.loading.GlobalLoadingManager
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import com.skapps.fakestoreapp.domain.entitiy.basket.BasketProductEntity
import com.skapps.fakestoreapp.domain.entitiy.favorites.FavoritesEntity
import com.skapps.fakestoreapp.domain.onError
import com.skapps.fakestoreapp.domain.onSuccess
import com.skapps.fakestoreapp.domain.usecase.basket.addorincrement.AddOrIncrementProductUseCase
import com.skapps.fakestoreapp.domain.usecase.favorites.add.AddProductToFavoritesUseCase
import com.skapps.fakestoreapp.domain.usecase.favorites.delete.DeleteProductToFavoritesUseCase
import com.skapps.fakestoreapp.domain.usecase.productdetail.GetProductDetailWithIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    loadingManager: GlobalLoadingManager,
    private val getProductDetailWithIdUseCase: GetProductDetailWithIdUseCase,
    private val addProductToFavoritesUseCase: AddProductToFavoritesUseCase,
    private val deleteProductFromFavoritesUseCase: DeleteProductToFavoritesUseCase,
    private val addOrIncrementProductUseCase: AddOrIncrementProductUseCase
) : BaseViewModel<ProductDetailUiState, ProductDetailUiAction, ProductDetailSideEffect>(
    initialState = ProductDetailUiState.EMPTY,
    globalLoadingManager = loadingManager
) {

    override fun onAction(uiAction: ProductDetailUiAction) {
        when (uiAction) {
            is ProductDetailUiAction.LoadProduct -> {
                getProductWithId(uiAction.productId)
            }

            is ProductDetailUiAction.FavoriteClicked -> {
                if (uiState.value.isFavorite) {
                    deleteProductFromFavorites(
                        id = uiState.value.id.toString(),
                        message = uiAction.loadingMessage
                    )
                } else {
                    addProductToFavorites(
                        favoritesEntity = uiState.value.toFavoritesEntity(),
                        message = uiAction.loadingMessage
                    )
                }
            }

            is ProductDetailUiAction.AddToCartClicked -> {
                addProductToBasket(
                    product = uiState.value,
                    message = uiAction.loadingMessage
                )
            }
        }
    }

    private fun getProductWithId(productId: String) {
        viewModelScope.launch {
            doWithGlobalLoading {
                getProductDetailWithIdUseCase(productId).mapSuccess {
                    it.toUiState()
                }.onSuccess {
                    updateUiState {
                        it
                    }
                }.onError {
                    emitSideEffect(ProductDetailSideEffect.ShowErrorGetProduct(it))
                }
            }
        }
    }

    private fun addProductToFavorites(favoritesEntity: FavoritesEntity, message: String) {
        viewModelScope.launch {
            doWithPartialLoading(
                block = {
                    addProductToFavoritesUseCase(favoritesEntity)
                        .onSuccess {
                            updateUiState {
                                copy(isFavorite = true)
                            }
                        }.onError {
                            emitSideEffect(ProductDetailSideEffect.ShowError(it))
                        }
                }, message = message
            )
        }
    }

    private fun deleteProductFromFavorites(id: String, message: String) {
        viewModelScope.launch {
            doWithPartialLoading(
                block = {
                    deleteProductFromFavoritesUseCase(id = id)
                        .onSuccess {
                            updateUiState {
                                copy(isFavorite = false)
                            }
                        }.onError {
                            emitSideEffect(ProductDetailSideEffect.ShowError(it))
                        }
                }, message = message
            )
        }
    }


    private fun addProductToBasket(product:ProductDetailUiState, message: String) {
        viewModelScope.launch {
            doWithPartialLoading(
                block = {
                    addOrIncrementProductUseCase(product.toBasketModel())
                        .onError {
                            emitSideEffect(ProductDetailSideEffect.ShowError(it))
                        }
                }, message = message
            )
        }
    }

    private fun ProductEntity.toUiState(): ProductDetailUiState {
        return ProductDetailUiState(
            id = this.id,
            title = this.title,
            description = this.description,
            thumbnail = this.thumbnail,
            price = this.newPrice,
            oldPrice = this.oldPrice,
            discountPercentage = this.discountPercentage,
            rating = this.rating,
            isFavorite = this.isFavorite
        )
    }

    private fun ProductDetailUiState.toFavoritesEntity(): FavoritesEntity {
        return FavoritesEntity(
            id = this.id,
            title = this.title,
            description = this.description,
            images = this.thumbnail,
            newPrice = this.price
        )
    }
    private fun ProductDetailUiState.toBasketModel(): BasketProductEntity {
        return BasketProductEntity(
            id = this.id,
            title = this.title,
            image = this.thumbnail,
            price = this.price,
            oldPrice = this.oldPrice,
            quantity = 1
        )
    }

}