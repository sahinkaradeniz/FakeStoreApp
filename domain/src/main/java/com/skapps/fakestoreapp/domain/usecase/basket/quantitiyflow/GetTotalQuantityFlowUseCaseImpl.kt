package com.skapps.fakestoreapp.domain.usecase.basket.quantitiyflow

import com.skapps.fakestoreapp.domain.repository.BasketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTotalQuantityFlowUseCaseImpl @Inject constructor(
    private val repository: BasketRepository
) : GetTotalQuantityFlowUseCase {
    override fun invoke(): Flow<Int?> {
        return repository.getTotalQuantityFlow()
    }
}