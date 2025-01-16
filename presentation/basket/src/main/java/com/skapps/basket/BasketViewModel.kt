package com.skapps.basket

import androidx.lifecycle.viewModelScope
import com.skapps.fakestoreapp.core.base.BaseViewModel
import com.skapps.fakestoreapp.core.loading.GlobalLoadingManager
import com.skapps.fakestoreapp.domain.onErrorWithMessage
import com.skapps.fakestoreapp.domain.onSuccess
import com.skapps.fakestoreapp.domain.usecase.basket.decrementorremove.DecrementBasketItemUseCase
import com.skapps.fakestoreapp.domain.usecase.basket.delete.DeleteBasketProductByIdUseCase
import com.skapps.fakestoreapp.domain.usecase.basket.getallproductsflow.GetAllBasketProductsFlowUseCase
import com.skapps.fakestoreapp.domain.usecase.basket.incrementquantity.IncrementBasketItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    loadingManager: GlobalLoadingManager,
    private val getBasketItemsUseCase: GetAllBasketProductsFlowUseCase,
    private val decrementBasketItemUseCase: DecrementBasketItemUseCase,
    private val incrementProductUseCase: IncrementBasketItemUseCase,
    private val deleteProductFromBasketUseCase: DeleteBasketProductByIdUseCase
) : BaseViewModel<BasketUiState, BasketUiAction, BasketSideEffect>(
    initialState = BasketUiState.EMPTY,
    globalLoadingManager = loadingManager
) {

    override fun onAction(uiAction: BasketUiAction) {
        when (uiAction) {
            is BasketUiAction.LoadBasket -> {
                loadBasket()
            }

            is BasketUiAction.IncreaseQuantity -> {
                incrementItem(uiAction.itemId, uiAction.loadingMessage)
            }

            is BasketUiAction.DecreaseQuantity -> {
                decrementItem(uiAction.itemId, uiAction.loadingMessage)
            }

            is BasketUiAction.RemoveItem -> {
                removeItem(uiAction.itemId, uiAction.loadingMessage)
            }

            is BasketUiAction.Checkout -> {
                viewModelScope.launch {
                    emitSideEffect(BasketSideEffect.CheckoutSuccess)
                }
            }
        }
    }

    private fun decrementItem(itemId: Int, message: String) {
        viewModelScope.launch {
            doWithPartialLoading(
                block = {
                    decrementBasketItemUseCase(itemId).onSuccess {
                        loadBasket()
                    }.onErrorWithMessage {
                        emitSideEffect(BasketSideEffect.ShowError(it))
                    }
                },
                message = message
            )
        }
    }

    private fun incrementItem(itemId: Int, message: String) {
        viewModelScope.launch {
            doWithPartialLoading(
                block = {
                    incrementProductUseCase(itemId).onSuccess {
                        loadBasket()
                    }.onErrorWithMessage {
                        emitSideEffect(BasketSideEffect.ShowError(it))
                    }
                },
                message = message
            )
        }
    }

    private fun loadBasket() {
        viewModelScope.launch {
                getBasketItemsUseCase().distinctUntilChanged().catch {
                    emitSideEffect(BasketSideEffect.ShowError(it.message ?: "Error"))
                }.collectLatest {
                    val uiList = it.map { basketEntity ->
                        BasketItemUiModel(
                            id = basketEntity.id,
                            title = basketEntity.title,
                            image = basketEntity.image,
                            price = basketEntity.price,
                            oldPrice = basketEntity.oldPrice,
                            quantity = basketEntity.quantity
                        )
                    }
                    updateUiState {
                        val totalPrice = uiList.sumOf { it.price * it.quantity }
                        val totalOldPrice = uiList.sumOf { it.oldPrice * it.quantity }
                        copy(
                            items = uiList,
                            totalPrice = totalPrice,
                            totalDiscount = (totalOldPrice - totalPrice).coerceAtLeast(0.0),
                            total = totalPrice - totalDiscount,
                            isEmpty = uiList.isEmpty()
                        )
                    }
                }
        }
    }


    private fun removeItem(itemId: Int, message: String) {
        viewModelScope.launch {
            doWithPartialLoading(
                block = {
                    deleteProductFromBasketUseCase(itemId).onSuccess {
                        loadBasket()
                    }.onErrorWithMessage {
                        emitSideEffect(BasketSideEffect.ShowError(it))
                    }
                },
                message = message
            )
        }
    }
}