package com.skapps.fakestoreapp.domain.usecase.searchpagedproducts

import androidx.paging.PagingData
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import kotlinx.coroutines.flow.Flow

interface SearchPagedProductsUseCase {
    suspend operator fun invoke(query:String): Flow<PagingData<ProductEntity>>
}