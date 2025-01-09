package com.skapps.fakestoreapp.domain.usecase.favorites.add

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import com.skapps.fakestoreapp.domain.entitiy.favorites.FavoritesEntity

interface AddProductToFavoritesUseCase {
    suspend operator fun invoke(productEntity: ProductEntity): IResult<FavoritesEntity, ApiErrorModel>
}