package com.skapps.fakestoreapp.domain.repository

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import com.skapps.fakestoreapp.domain.entitiy.favorites.FavoritesEntity

interface FavoriteProductsRepository {
    suspend fun addProductToFavorites(product: ProductEntity): IResult<FavoritesEntity, ApiErrorModel>
    suspend fun deleteProductFromFavorites(product: FavoritesEntity): IResult<FavoritesEntity, ApiErrorModel>
    suspend fun getAllFavoriteProducts(): IResult<List<FavoritesEntity>, ApiErrorModel>
    suspend fun getFavoriteProductWithId(product: FavoritesEntity): IResult<FavoritesEntity, ApiErrorModel>
}