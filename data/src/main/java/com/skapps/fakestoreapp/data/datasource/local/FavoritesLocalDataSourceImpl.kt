package com.skapps.fakestoreapp.data.datasource.local

import com.skapps.fakestoreapp.data.di.Dispatcher
import com.skapps.fakestoreapp.data.di.DispatcherType
import com.skapps.fakestoreapp.data.models.favorites.FavoritesDbModel
import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.UiError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoritesLocalDataSourceImpl @Inject constructor(
    private val favoritesDao: FavoritesDao,
    @Dispatcher(DispatcherType.Io) private val dispatcher: CoroutineDispatcher
) : FavoritesLocalDataSource {
    override suspend fun addProductToFavorites(favoritesDbModel: FavoritesDbModel): IResult<FavoritesDbModel, ApiErrorModel> =
        withContext(dispatcher) {
            try {
                favoritesDao.addProductToFavorites(favoritesDbModel)
                IResult.Success(favoritesDbModel)
            } catch (e: Exception) {
                IResult.Error(
                    UiError.IO(
                        e.message ?: "An error occurred while adding product to favorites"
                    )
                )
            }
        }

    override suspend fun deleteProductToFavorites(favoritesDbModel: FavoritesDbModel): IResult<FavoritesDbModel, ApiErrorModel> =
        withContext(dispatcher) {
            try {
                favoritesDao.deleteProductToFavorites(favoritesDbModel)
                IResult.Success(favoritesDbModel)
            } catch (e: Exception) {
                IResult.Error(
                    UiError.IO(
                        e.message ?: "An error occurred while deleting product from favorites"
                    )
                )
            }
        }

    override suspend fun getAllProducts(): IResult<List<FavoritesDbModel>, ApiErrorModel> =
        withContext(dispatcher) {
            try {
                IResult.Success(favoritesDao.getAllProducts())
            } catch (e: Exception) {
                IResult.Error(
                    UiError.IO(
                        e.message ?: "An error occurred while getting all products from favorites"
                    )
                )
            }
        }

    override suspend fun getFavoriteProductWithId(id: Int): IResult<FavoritesDbModel, ApiErrorModel> =
        withContext(dispatcher) {
            try {
                IResult.Success(favoritesDao.getFavoriteProductWithId(id))
            } catch (e: Exception) {
                IResult.Error(
                    UiError.IO(
                        e.message ?: "An error occurred while getting product from favorites"
                    )
                )
            }
        }
}
