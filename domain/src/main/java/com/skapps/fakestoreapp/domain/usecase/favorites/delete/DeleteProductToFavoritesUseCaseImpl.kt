package com.skapps.fakestoreapp.domain.usecase.favorites.delete

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.favorites.FavoritesEntity
import com.skapps.fakestoreapp.domain.repository.FavoriteProductsRepository
import javax.inject.Inject

class DeleteProductToFavoritesUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoriteProductsRepository
): DeleteProductToFavoritesUseCase {
    override suspend fun invoke(favoritesEntity: FavoritesEntity): IResult<FavoritesEntity, ApiErrorModel> {
        return favoritesRepository.deleteProductFromFavorites(favoritesEntity)
    }
}
