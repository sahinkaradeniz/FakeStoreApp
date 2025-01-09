package com.skapps.fakestoreapp.di

import com.skapps.fakestoreapp.domain.usecase.favorites.add.AddProductToFavoritesUseCase
import com.skapps.fakestoreapp.domain.usecase.favorites.add.AddProductToFavoritesUseCaseImpl
import com.skapps.fakestoreapp.domain.usecase.favorites.delete.DeleteProductToFavoritesUseCase
import com.skapps.fakestoreapp.domain.usecase.favorites.delete.DeleteProductToFavoritesUseCaseImpl
import com.skapps.fakestoreapp.domain.usecase.favorites.getAll.GetAllFavoriteProductsUseCase
import com.skapps.fakestoreapp.domain.usecase.favorites.getAll.GetAllFavoriteProductsUseCaseImpl
import com.skapps.fakestoreapp.domain.usecase.getpagedproducts.GetPagedProductsUseCase
import com.skapps.fakestoreapp.domain.usecase.getpagedproducts.GetPagedProductsUseCaseImpl
import com.skapps.fakestoreapp.domain.usecase.searchpagedproducts.SearchPagedProductsUseCase
import com.skapps.fakestoreapp.domain.usecase.searchpagedproducts.SearchPagedProductsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    @Singleton
    abstract fun bindGetPagedProductsUseCase(
        getPagedProductsUseCaseImpl: GetPagedProductsUseCaseImpl
    ): GetPagedProductsUseCase

    @Binds
    @Singleton
    abstract fun bindSearchPagedProductsUseCase(
        searchPagedProductsUseCaseImpl: SearchPagedProductsUseCaseImpl
    ): SearchPagedProductsUseCase

    @Binds
    @Singleton
    abstract fun bindAddProductToFavoritesUseCase(
        addProductToFavoritesUseCaseImpl: AddProductToFavoritesUseCaseImpl
    ): AddProductToFavoritesUseCase

    @Binds
    @Singleton
    abstract fun bindGetAllFavoriteProductsUseCase(
        getAllFavoriteProductsUseCaseImpl: GetAllFavoriteProductsUseCaseImpl
    ): GetAllFavoriteProductsUseCase


    @Binds
    @Singleton
    abstract fun bindDeleteFavoriteProductUseCase(
        deleteFavoriteProductUseCaseImpl: DeleteProductToFavoritesUseCaseImpl
    ): DeleteProductToFavoritesUseCase
}