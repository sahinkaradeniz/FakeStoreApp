package com.skapps.fakestoreapp.data.datasource.local

import com.skapps.fakestoreapp.data.models.favorites.FavoritesDbModel
import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult

interface FavoritesLocalDataSource {

    suspend fun addProductToFavorites(favoritesDbModel: FavoritesDbModel): IResult<FavoritesDbModel, ApiErrorModel>

    suspend fun deleteProductToFavorites(id:String): IResult<String, ApiErrorModel>

    suspend fun getAllProducts(): IResult<List<FavoritesDbModel>, ApiErrorModel>

    suspend fun getFavoriteProductWithId(id: Int): IResult<FavoritesDbModel, ApiErrorModel>
}