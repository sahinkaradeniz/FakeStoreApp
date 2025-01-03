package com.skapps.fakestoreapp.di

import com.skapps.fakestoreapp.domain.usecase.GetAllProductsUseCase
import com.skapps.fakestoreapp.domain.usecase.GetAllProductsUseCaseImpl
import com.skapps.fakestoreapp.domain.usecase.GetPagedProductsUseCase
import com.skapps.fakestoreapp.domain.usecase.GetPagedProductsUseCaseImpl
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
}