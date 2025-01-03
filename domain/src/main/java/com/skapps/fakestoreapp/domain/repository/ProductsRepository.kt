package com.skapps.fakestoreapp.domain.repository
import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.GetProductsListParams
import com.skapps.fakestoreapp.domain.entitiy.ProductsListEntity

interface ProductsRepository {
    suspend fun getProductsList(
        params: GetProductsListParams
    ): IResult<ProductsListEntity, ApiErrorModel>
}