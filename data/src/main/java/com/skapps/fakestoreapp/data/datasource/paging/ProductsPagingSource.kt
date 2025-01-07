package com.skapps.fakestoreapp.data.datasource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skapps.fakestoreapp.data.datasource.remote.ProductsListRemoteSource
import com.skapps.fakestoreapp.data.mapper.toEntity
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import com.skapps.fakestoreapp.domain.entitiy.SortType
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class ProductsPagingSource @AssistedInject constructor(
    @Assisted private val sortType: SortType,
    private val productsListRemoteSource: ProductsListRemoteSource
) : PagingSource<Int, ProductEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductEntity> {
        return try {
            val currentPage = params.key ?: 1

            val (sortBy, order) = when (sortType) {
                SortType.NONE -> null to null
                SortType.PRICE_ASC -> "price" to "asc"
                SortType.PRICE_DESC -> "price" to "desc"
                SortType.TITLE_ASC -> "title" to "asc"
                SortType.TITLE_DESC -> "title" to "desc"
            }

            val response = productsListRemoteSource.getAllProducts(
                skip = currentPage, limit = params.loadSize, sortBy = sortBy,
                order = order
            )
            if (!response.isSuccessful) {
                return LoadResult.Error(Exception("Failed to load products"))
            }
            val rawProducts = response.body()?.toEntity()?.products.orEmpty()
            val sortedProducts = when (sortType) {
                SortType.NONE -> rawProducts
                SortType.PRICE_ASC -> rawProducts.sortedBy { it.newPrice }
                SortType.PRICE_DESC -> rawProducts.sortedByDescending { it.newPrice }
                SortType.TITLE_ASC -> rawProducts.sortedBy { it.title }
                SortType.TITLE_DESC -> rawProducts.sortedByDescending { it.title }
            }

            LoadResult.Page(
                data = sortedProducts,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (sortedProducts.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProductEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val page = state.closestPageToPosition(anchorPosition)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }

}