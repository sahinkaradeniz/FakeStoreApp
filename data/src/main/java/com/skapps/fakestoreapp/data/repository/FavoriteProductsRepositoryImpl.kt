package com.skapps.fakestoreapp.data.repository

import com.skapps.fakestoreapp.data.datasource.local.FavoritesLocalDataSource
import com.skapps.fakestoreapp.data.di.Dispatcher
import com.skapps.fakestoreapp.data.di.DispatcherType
import com.skapps.fakestoreapp.data.mapper.toDbModel
import com.skapps.fakestoreapp.data.mapper.toEntity
import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import com.skapps.fakestoreapp.domain.entitiy.favorites.FavoritesEntity
import com.skapps.fakestoreapp.domain.repository.FavoriteProductsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteProductsRepositoryImpl @Inject constructor(
    private val favoritesLocalDataSource: FavoritesLocalDataSource,
    @Dispatcher(DispatcherType.Io) private val dispatcher: CoroutineDispatcher
) : FavoriteProductsRepository {
    override suspend fun addProductToFavorites(product: ProductEntity): IResult<FavoritesEntity, ApiErrorModel> =
        withContext(dispatcher) {
            favoritesLocalDataSource.addProductToFavorites(product.toDbModel())
                .mapSuccess { it?.toEntity() }
        }

    override suspend fun deleteProductFromFavorites(favoritesEntity: FavoritesEntity): IResult<FavoritesEntity, ApiErrorModel> =
        withContext(dispatcher) {
            favoritesLocalDataSource.deleteProductToFavorites(favoritesEntity.toDbModel())
                .mapSuccess { it?.toEntity() }
        }


    override suspend fun getAllFavoriteProducts(): IResult<List<FavoritesEntity>, ApiErrorModel> =
        withContext(dispatcher) {
            favoritesLocalDataSource.getAllProducts()
                .mapSuccess { it?.map { it.toEntity() } }
        }


    override suspend fun getFavoriteProductWithId(product: FavoritesEntity): IResult<FavoritesEntity, ApiErrorModel> =
        withContext(dispatcher) {
            favoritesLocalDataSource.getFavoriteProductWithId(product.id)
                .mapSuccess { it?.toEntity() }
        }

}