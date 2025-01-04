package com.skapps.fakestoreapp.domain.usecase

import androidx.paging.PagingData
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import kotlinx.coroutines.flow.Flow

interface GetPagedProductsUseCase  {
    suspend operator fun invoke(pageSize: Int): Flow<PagingData<ProductEntity>>
}