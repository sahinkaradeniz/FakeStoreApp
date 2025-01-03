package com.skapps.fakestoreapp.data.datasource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skapps.fakestoreapp.data.datasource.remote.ProductsListRemoteSource
import com.skapps.fakestoreapp.data.mapper.toEntity
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import javax.inject.Inject

class ProductsPagingSource @Inject constructor(
    private val productsListRemoteSource: ProductsListRemoteSource
) : PagingSource<Int, ProductEntity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductEntity> {
        return try {
            val currentPage = params.key ?: 1
            val response = productsListRemoteSource.getAllProducts(skip = currentPage, limit = params.loadSize)
            if (!response.isSuccessful) {
                return LoadResult.Error(Exception("Failed to load products"))
            }

            LoadResult.Page(
                data = response.body()?.toEntity()?.products.orEmpty(),
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (response.body()?.toEntity()?.products?.isEmpty() == true) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProductEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}