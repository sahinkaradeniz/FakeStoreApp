package com.skapps.fakestoreapp.data.repository

import androidx.paging.PagingSource
import com.skapps.fakestoreapp.data.datasource.paging.ProductsPagingSource
import com.skapps.fakestoreapp.data.datasource.remote.ProductsListRemoteSource
import com.skapps.fakestoreapp.data.mapper.parseError
import com.skapps.fakestoreapp.data.mapper.toEntity
import com.skapps.fakestoreapp.data.network.apiexecutor.ApiExecutor
import com.skapps.fakestoreapp.data.network.apiexecutor.ApiResult
import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.GetProductsListParams
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import com.skapps.fakestoreapp.domain.entitiy.ProductsListEntity
import com.skapps.fakestoreapp.domain.repository.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl  @Inject constructor(
    private val remoteSourceImpl: ProductsListRemoteSource,
    private val productsPagingSource: ProductsPagingSource,
    private val apiExecutorImpl: ApiExecutor
):ProductsRepository{

    override suspend fun getProductsList(
        params: GetProductsListParams
    ): IResult<ProductsListEntity, ApiErrorModel> {
        val result = apiExecutorImpl.execute(
            call = {
                remoteSourceImpl.getAllProducts(params.limit, params.skip)
            }
        )

        return when(result){
            is ApiResult.Error ->{
                IResult.Error(parseError<ApiErrorModel>(result).error)
            }
            is ApiResult.Success -> {
                IResult.Success(result.response?.toEntity())
            }
        }
    }

    override fun getProductsPagingSource(): PagingSource<Int, ProductEntity> {
        return productsPagingSource
    }

}