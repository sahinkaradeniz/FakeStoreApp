package com.skapps.fakestoreapp.domain.usecase.favorites.getAll

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.favorites.FavoritesEntity


interface GetAllFavoriteProductsUseCase {
    suspend operator fun invoke(): IResult<List<FavoritesEntity>, ApiErrorModel>
}