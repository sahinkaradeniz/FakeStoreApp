package com.skapps.fakestoreapp.domain.usecase.basket.delete

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.repository.BasketRepository
import javax.inject.Inject

class DeleteProductByIdUseCaseImpl @Inject constructor(
    private val repository: BasketRepository
) : DeleteProductByIdUseCase {
    override suspend fun invoke(id: Int): IResult<Int, ApiErrorModel> {
        return repository.deleteProductById(id)
    }
}