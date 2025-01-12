package com.skapps.fakestoreapp.domain.usecase.basket.getallproductsflow

import com.skapps.fakestoreapp.domain.entitiy.basket.BasketProductEntity
import kotlinx.coroutines.flow.Flow


interface GetAllProductsFlowUseCase {
    operator fun invoke(): Flow<List<BasketProductEntity>>
}