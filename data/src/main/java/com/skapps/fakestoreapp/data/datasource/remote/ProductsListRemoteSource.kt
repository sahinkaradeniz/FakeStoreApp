package com.skapps.fakestoreapp.data.datasource.remote
import com.skapps.fakestoreapp.data.models.ProductsResponseDto
import retrofit2.Response

interface ProductsListRemoteSource {
    suspend fun getAllProducts(limit: Int, skip: Int): Response<ProductsResponseDto>
}