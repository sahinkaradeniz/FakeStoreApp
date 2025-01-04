package com.skapps.fakestoreapp.domain.usecase.getallproducts

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.GetProductsListParams
import com.skapps.fakestoreapp.domain.entitiy.ProductsListEntity

interface GetAllProductsUseCase {
    suspend operator fun invoke(params: GetProductsListParams): IResult<ProductsListEntity, ApiErrorModel>
}