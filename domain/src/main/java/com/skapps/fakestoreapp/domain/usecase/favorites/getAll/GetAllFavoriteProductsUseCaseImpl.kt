package com.skapps.fakestoreapp.domain.usecase.favorites.getAll

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.favorites.FavoritesEntity
import com.skapps.fakestoreapp.domain.repository.FavoriteProductsRepository
import javax.inject.Inject

class GetAllFavoriteProductsUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoriteProductsRepository
) : GetAllFavoriteProductsUseCase {
    override suspend operator fun invoke(): IResult<List<FavoritesEntity>, ApiErrorModel> {
        return favoritesRepository.getAllFavoriteProducts()
    }
}