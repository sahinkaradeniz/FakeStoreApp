package com.skapps.fakestoreapp.domain.usecase.favorites.add

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import com.skapps.fakestoreapp.domain.entitiy.favorites.FavoritesEntity
import com.skapps.fakestoreapp.domain.repository.FavoriteProductsRepository
import javax.inject.Inject

class AddProductToFavoritesUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoriteProductsRepository
): AddProductToFavoritesUseCase {
    override suspend fun invoke(productEntity: ProductEntity): IResult<FavoritesEntity, ApiErrorModel> {
        return favoritesRepository.addProductToFavorites(productEntity)
    }
}