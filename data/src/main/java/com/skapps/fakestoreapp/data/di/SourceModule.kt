package com.skapps.fakestoreapp.data.di

import com.skapps.fakestoreapp.data.datasource.remote.ProductsListRemoteSource
import com.skapps.fakestoreapp.data.datasource.remote.ProductsListRemoteSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract  class SourceModule {

    @Binds
    @Singleton
    abstract fun bindProductsListRemoteSource(productsListRemoteSourceImpl: ProductsListRemoteSourceImpl): ProductsListRemoteSource
}