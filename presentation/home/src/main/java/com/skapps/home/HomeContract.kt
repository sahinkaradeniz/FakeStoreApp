package com.skapps.home

import androidx.paging.PagingData
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow



data class HomeUiState(
    val products: Flow<PagingData<ProductEntity>>
)


sealed class HomeUiAction {
    data object LoadPagedProducts : HomeUiAction()
}

sealed class HomeSideEffect {
    data class ShowError(val message: String) : HomeSideEffect()
}