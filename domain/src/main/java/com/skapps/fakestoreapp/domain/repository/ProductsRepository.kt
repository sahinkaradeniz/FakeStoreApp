package com.skapps.fakestoreapp.domain.repository
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.GetProductsListParams
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import com.skapps.fakestoreapp.domain.entitiy.ProductsListEntity
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun getProductsList(
        params: GetProductsListParams
    ): IResult<ProductsListEntity, ApiErrorModel>

    fun getProductsPagingSource(): Flow<PagingData<ProductEntity>>

    suspend fun searchProducts(
        query: String,
    ): Flow<PagingData<ProductEntity>>
}