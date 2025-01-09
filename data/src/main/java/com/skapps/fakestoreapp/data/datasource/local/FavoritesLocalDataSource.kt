package com.skapps.fakestoreapp.data.datasource.local

import com.skapps.fakestoreapp.data.models.favorites.FavoritesDbModel
import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.favorites.FavoritesEntity

interface FavoritesLocalDataSource {

    suspend fun addProductToFavorites(product: FavoritesDbModel): IResult<FavoritesDbModel, ApiErrorModel>

    suspend fun deleteProductToFavorites(product: FavoritesDbModel): IResult<FavoritesDbModel, ApiErrorModel>

    suspend fun getAllProducts(): IResult<List<FavoritesDbModel>, ApiErrorModel>

    suspend fun getFavoriteProductWithId(id: Int): IResult<FavoritesDbModel, ApiErrorModel>
}