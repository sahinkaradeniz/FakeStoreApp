package com.skapps.home

import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import com.skapps.fakestoreapp.domain.entitiy.ProductsListEntity


/**
 * UI'de gösterilecek durumları tutar.
 * Burada örnek olarak sadece ürün listesini tuttuk.
 */
data class HomeUiState(
    val products: List<ProductEntity> = emptyList()
)

/**
 * Kullanıcı veya sistem tarafından tetiklenebilecek aksiyonları tanımlar.
 */
sealed class HomeUiAction {
    object LoadProducts : HomeUiAction()
}

/**
 * UI'ye tek seferlik yan etkiler (örn. Toast mesajı, Navigation gibi).
 */
sealed class HomeSideEffect {
    data class ShowError(val message: String) : HomeSideEffect()
}