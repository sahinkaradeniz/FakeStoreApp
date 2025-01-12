package com.skapps.fakestoreapp.domain.usecase.basket.incrementquantity

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.repository.BasketRepository
import javax.inject.Inject

class IncrementQuantityUseCaseImpl @Inject constructor(
    private val repository: BasketRepository
) : IncrementQuantityUseCase {
    override suspend fun invoke(id: Int): IResult<Unit, ApiErrorModel> {
        return repository.incrementQuantity(id)
    }
}