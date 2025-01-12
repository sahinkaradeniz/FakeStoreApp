package com.skapps.fakestoreapp.domain.usecase.basket.decrementorremove

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.repository.BasketRepository
import javax.inject.Inject

class DecrementQuantityOrRemoveUseCaseImpl @Inject constructor(
    private val repository: BasketRepository
) : DecrementQuantityOrRemoveUseCase {
    override suspend fun invoke(id: Int): IResult<Unit, ApiErrorModel> {
        return repository.decrementQuantityOrRemove(id)
    }
}