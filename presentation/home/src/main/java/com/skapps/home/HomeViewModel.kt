package com.skapps.home

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.skapps.common.components.handleActionWithResult
import com.skapps.fakestoreapp.core.base.BaseViewModel
import com.skapps.fakestoreapp.core.loading.GlobalLoadingManager
import com.skapps.fakestoreapp.domain.entitiy.GetProductsListParams
import com.skapps.fakestoreapp.domain.usecase.GetAllProductsUseCase
import com.skapps.fakestoreapp.domain.usecase.GetPagedProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    loadingManager: GlobalLoadingManager,
    private val getPagedProductsUseCase: GetPagedProductsUseCase
) : BaseViewModel<HomeUiState, HomeUiAction, HomeSideEffect>(
    initialState = HomeUiState(products = emptyFlow()),
    globalLoadingManager = loadingManager
) {

    init {
        onAction(HomeUiAction.LoadPagedProducts)
    }

    override fun onAction(uiAction: HomeUiAction) {
        when (uiAction) {
            is HomeUiAction.LoadPagedProducts ->{
                loadPagedProducts()
            }
        }
    }

    private fun loadPagedProducts() {
        viewModelScope.launch {
            val flow = getPagedProductsUseCase(pageSize = PRODUCTS_PAGE_SIZE)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
            updateUiState { copy(products = flow) }
        }
    }


    companion object {
       private const val PRODUCTS_PAGE_SIZE = 10
    }

}