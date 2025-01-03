package com.skapps.home

import androidx.lifecycle.viewModelScope
import com.skapps.common.components.handleActionWithResult
import com.skapps.fakestoreapp.core.base.BaseViewModel
import com.skapps.fakestoreapp.core.loading.GlobalLoadingManager
import com.skapps.fakestoreapp.domain.entitiy.GetProductsListParams
import com.skapps.fakestoreapp.domain.usecase.GetAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    loadingManager: GlobalLoadingManager,
    private val getAllProductsUseCase: GetAllProductsUseCase
) : BaseViewModel<HomeUiState, HomeUiAction, HomeSideEffect>(
    initialState = HomeUiState(),
    globalLoadingManager = loadingManager
) {

    init {
        onAction(HomeUiAction.LoadProducts)
    }

    override fun onAction(uiAction: HomeUiAction) {
        when (uiAction) {
            is HomeUiAction.LoadProducts -> {
                loadProducts()
            }
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            doWithGlobalLoading {
                getAllProductsUseCase(GetProductsListParams(10, 10))
            }.handleActionWithResult(
                onSuccess = { products ->
                    updateUiState{ copy(products = products?.products ?: emptyList()) }
                },
                onError = { error ->
                    emitSideEffect(HomeSideEffect.ShowError(error.toString()))
                }
            )
        }
    }


}