package com.skapps.fakestoreapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.skapps.fakestoreapp.data.datasource.paging.ProductsPagingSource
import com.skapps.fakestoreapp.data.datasource.paging.SearchProductsPagingSource
import com.skapps.fakestoreapp.domain.entitiy.GetPagedProductsParams
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import com.skapps.fakestoreapp.domain.entitiy.SearchPagedProductParams
import com.skapps.fakestoreapp.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val productsPagingSource: ProductsPagingSource,
    private val searchProductsPagingSource: SearchProductsPagingSource
) : ProductsRepository {

    override fun getProductsPagingSource(params: GetPagedProductsParams): Flow<PagingData<ProductEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = params.pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                productsPagingSource.apply {
                    sortType = params.sortType
                }
            }
        ).flow
    }

    override suspend fun searchProducts(
        params: SearchPagedProductParams
    ): Flow<PagingData<ProductEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = params.pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                searchProductsPagingSource.apply {
                    query = params.query
                    sortType = params.sortType
                }
            }
        ).flow
    }

}