package com.skapps.home

import androidx.paging.PagingData
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


/**
 * UI'de gösterilecek durumları tutar.
 * Burada örnek olarak sadece ürün listesini tuttuk.
 */
data class HomeUiState(
    val products: Flow<PagingData<ProductEntity>>? = null
)

/**
 * Kullanıcı veya sistem tarafından tetiklenebilecek aksiyonları tanımlar.
 */
sealed class HomeUiAction {
    data object LoadPagedProducts : HomeUiAction()
}

/**
 * UI'ye tek seferlik yan etkiler (örn. Toast mesajı, Navigation gibi).
 */
sealed class HomeSideEffect {
    data class ShowError(val message: String) : HomeSideEffect()
}