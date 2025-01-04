package com.skapps.home

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.skapps.fakestoreapp.core.base.BaseViewModel
import com.skapps.fakestoreapp.core.loading.GlobalLoadingManager
import com.skapps.fakestoreapp.coreui.theme.logError
import com.skapps.fakestoreapp.domain.usecase.getpagedproducts.GetPagedProductsUseCase
import com.skapps.fakestoreapp.domain.usecase.searchpagedproducts.SearchPagedProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    loadingManager: GlobalLoadingManager,
    private val getPagedProductsUseCase: GetPagedProductsUseCase,
    private val searchPagedProductsUseCase: SearchPagedProductsUseCase
) : BaseViewModel<HomeUiState, HomeUiAction, HomeSideEffect>(
    initialState = HomeUiState(products = emptyFlow(), searchResults = emptyFlow()),
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
            is HomeUiAction.ProductClicked -> {
               viewModelScope.emitSideEffect(HomeSideEffect.NavigateToProductDetail(uiAction.id))
            }

            is HomeUiAction.ClearSearch ->{
                updateUiState { copy(query = "", isSearchMode = false) }
                viewModelScope.emitSideEffect(HomeSideEffect.ShowError("Search Cleared"))
            }
            is HomeUiAction.SearchQueryChanged -> {
                updateUiState { copy(query = uiAction.query) }
                if (uiAction.query.length > 1) {
                    loadSearchResults(uiAction.query)
                }
            }
        }
    }

    private fun loadPagedProducts() {
        viewModelScope.launch {
            val flow = getPagedProductsUseCase()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
            updateUiState { copy(products = flow) }
        }
    }

    private fun loadSearchResults(query: String) {
        viewModelScope.launch {
            val flow = searchPagedProductsUseCase(query)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
            updateUiState { copy(searchResults = flow, isSearchMode = true) }
        }
    }

    fun onProductClicked(id: String) {
        onAction(HomeUiAction.ProductClicked(id))
    }

    fun onSearchQueryChanged(query: String) {
        onAction(HomeUiAction.SearchQueryChanged(query))
    }

    fun onClearSearch() {
        onAction(HomeUiAction.ClearSearch)
    }


}