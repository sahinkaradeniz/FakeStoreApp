package com.skapps.fakestoreapp.domain.usecase.getallproducts

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.GetProductsListParams
import com.skapps.fakestoreapp.domain.entitiy.ProductsListEntity
import com.skapps.fakestoreapp.domain.repository.ProductsRepository
import javax.inject.Inject

class GetAllProductsUseCaseImpl @Inject constructor(
    private val productsRepository: ProductsRepository
): GetAllProductsUseCase {
    override suspend fun invoke(
        params: GetProductsListParams
    ): IResult<ProductsListEntity, ApiErrorModel> {
        return productsRepository.getProductsList(params)
    }
}