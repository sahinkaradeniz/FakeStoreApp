package com.skapps.detail

import androidx.lifecycle.viewModelScope
import com.skapps.fakestoreapp.core.base.BaseViewModel
import com.skapps.fakestoreapp.core.loading.GlobalLoadingManager
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import com.skapps.fakestoreapp.domain.onError
import com.skapps.fakestoreapp.domain.onSuccess
import com.skapps.fakestoreapp.domain.usecase.productdetail.GetProductDetailWithIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    loadingManager: GlobalLoadingManager,
    private val getProductDetailWithIdUseCase: GetProductDetailWithIdUseCase
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

            }

            is ProductDetailUiAction.AddToCartClicked -> {

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
}