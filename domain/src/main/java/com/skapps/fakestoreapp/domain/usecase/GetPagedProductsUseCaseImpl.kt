package com.skapps.fakestoreapp.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.skapps.fakestoreapp.domain.entitiy.GetProductsListParams
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import com.skapps.fakestoreapp.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagedProductsUseCaseImpl @Inject constructor(
    private val productsRepository: ProductsRepository
):GetPagedProductsUseCase {
    override suspend fun invoke(pageSize: Int): Flow<PagingData<ProductEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { productsRepository.getProductsPagingSource() }
        ).flow
    }
}