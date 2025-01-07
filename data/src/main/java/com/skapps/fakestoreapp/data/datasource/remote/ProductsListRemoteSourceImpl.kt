package com.skapps.fakestoreapp.data.datasource.remote
import com.skapps.fakestoreapp.data.models.ProductsResponseDto
import com.skapps.fakestoreapp.data.network.api.FakeStoreApi
import retrofit2.Response
import javax.inject.Inject

class ProductsListRemoteSourceImpl @Inject constructor(
    private val fakeStoreApi: FakeStoreApi
):ProductsListRemoteSource{

    override suspend fun getAllProducts(
        limit: Int,
        skip: Int,
        sortBy: String?,
        order: String?
    ): Response<ProductsResponseDto> {
        return fakeStoreApi.getAllProducts(limit, skip, sortBy, order)
    }

    override suspend fun search(
        query: String,
        limit: Int,
        skip: Int
    ): Response<ProductsResponseDto> {
        return fakeStoreApi.search(query, limit, skip)
    }
}