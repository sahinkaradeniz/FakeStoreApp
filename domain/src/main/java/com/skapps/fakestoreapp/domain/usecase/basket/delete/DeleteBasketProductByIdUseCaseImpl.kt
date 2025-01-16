package com.skapps.fakestoreapp.domain.usecase.basket.delete

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.repository.BasketRepository
import javax.inject.Inject

class DeleteBasketProductByIdUseCaseImpl @Inject constructor(
    private val repository: BasketRepository
) : DeleteBasketProductByIdUseCase {
    override suspend fun invoke(id: Int): IResult<Int, ApiErrorModel> {
        return repository.deleteProductById(id)
    }
}