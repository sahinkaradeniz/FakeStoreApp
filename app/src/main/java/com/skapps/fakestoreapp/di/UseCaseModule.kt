package com.skapps.fakestoreapp.di

import com.skapps.fakestoreapp.domain.usecase.getallproducts.GetAllProductsUseCase
import com.skapps.fakestoreapp.domain.usecase.getallproducts.GetAllProductsUseCaseImpl
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
    abstract fun bindGetProductsListUseCase(
        getProductsListUseCaseImpl: GetAllProductsUseCaseImpl
    ): GetAllProductsUseCase

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
}