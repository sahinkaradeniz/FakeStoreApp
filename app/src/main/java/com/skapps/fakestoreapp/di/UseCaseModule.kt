package com.skapps.fakestoreapp.di

import com.skapps.fakestoreapp.domain.usecase.basket.addorincrement.AddOrIncrementProductUseCase
import com.skapps.fakestoreapp.domain.usecase.basket.addorincrement.AddOrIncrementProductUseCaseImpl
import com.skapps.fakestoreapp.domain.usecase.basket.clear.ClearBasketUseCase
import com.skapps.fakestoreapp.domain.usecase.basket.clear.ClearBasketUseCaseImpl
import com.skapps.fakestoreapp.domain.usecase.basket.decrementorremove.DecrementQuantityOrRemoveUseCase
import com.skapps.fakestoreapp.domain.usecase.basket.decrementorremove.DecrementQuantityOrRemoveUseCaseImpl
import com.skapps.fakestoreapp.domain.usecase.basket.delete.DeleteProductByIdUseCase
import com.skapps.fakestoreapp.domain.usecase.basket.delete.DeleteProductByIdUseCaseImpl
import com.skapps.fakestoreapp.domain.usecase.basket.getallonce.GetAllProductsOnceUseCase
import com.skapps.fakestoreapp.domain.usecase.basket.getallonce.GetAllProductsOnceUseCaseImpl
import com.skapps.fakestoreapp.domain.usecase.basket.getallproductsflow.GetAllProductsFlowUseCase
import com.skapps.fakestoreapp.domain.usecase.basket.getallproductsflow.GetAllProductsFlowUseCaseImpl
import com.skapps.fakestoreapp.domain.usecase.basket.incrementquantity.IncrementQuantityUseCase
import com.skapps.fakestoreapp.domain.usecase.basket.incrementquantity.IncrementQuantityUseCaseImpl
import com.skapps.fakestoreapp.domain.usecase.basket.quantitiyflow.GetTotalQuantityFlowUseCase
import com.skapps.fakestoreapp.domain.usecase.basket.quantitiyflow.GetTotalQuantityFlowUseCaseImpl
import com.skapps.fakestoreapp.domain.usecase.basket.upsert.UpsertProductUseCase
import com.skapps.fakestoreapp.domain.usecase.basket.upsert.UpsertProductUseCaseImpl
import com.skapps.fakestoreapp.domain.usecase.favorites.add.AddProductToFavoritesUseCase
import com.skapps.fakestoreapp.domain.usecase.favorites.add.AddProductToFavoritesUseCaseImpl
import com.skapps.fakestoreapp.domain.usecase.favorites.delete.DeleteProductToFavoritesUseCase
import com.skapps.fakestoreapp.domain.usecase.favorites.delete.DeleteProductToFavoritesUseCaseImpl
import com.skapps.fakestoreapp.domain.usecase.favorites.getAll.GetAllFavoriteProductsUseCase
import com.skapps.fakestoreapp.domain.usecase.favorites.getAll.GetAllFavoriteProductsUseCaseImpl
import com.skapps.fakestoreapp.domain.usecase.getpagedproducts.GetPagedProductsUseCase
import com.skapps.fakestoreapp.domain.usecase.getpagedproducts.GetPagedProductsUseCaseImpl
import com.skapps.fakestoreapp.domain.usecase.productdetail.GetProductDetailWithIdUseCase
import com.skapps.fakestoreapp.domain.usecase.productdetail.GetProductDetailWithIdUseCaseImpl
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

    /**
     *   PRODUCTS
     **/

    @Binds
    @Singleton
    abstract fun bindGetPagedProductsUseCase(
        impl: GetPagedProductsUseCaseImpl
    ): GetPagedProductsUseCase

    @Binds
    @Singleton
    abstract fun bindSearchPagedProductsUseCase(
        impl: SearchPagedProductsUseCaseImpl
    ): SearchPagedProductsUseCase

    @Binds
    @Singleton
    abstract fun bindGetProductDetailWithIdUseCase(
        impl: GetProductDetailWithIdUseCaseImpl
    ): GetProductDetailWithIdUseCase

    /**
     *   FAVORITES
     **/

    @Binds
    @Singleton
    abstract fun bindAddProductToFavoritesUseCase(
        impl: AddProductToFavoritesUseCaseImpl
    ): AddProductToFavoritesUseCase

    @Binds
    @Singleton
    abstract fun bindGetAllFavoriteProductsUseCase(
        impl: GetAllFavoriteProductsUseCaseImpl
    ): GetAllFavoriteProductsUseCase

    @Binds
    @Singleton
    abstract fun bindDeleteFavoriteProductUseCase(
        impl: DeleteProductToFavoritesUseCaseImpl
    ): DeleteProductToFavoritesUseCase


    /**
     *  BASKET
     **/

    @Binds
    @Singleton
    abstract fun bindUpsertProductUseCase(
        impl: UpsertProductUseCaseImpl
    ): UpsertProductUseCase

    @Binds
    @Singleton
    abstract fun bindDeleteProductByIdUseCase(
        impl: DeleteProductByIdUseCaseImpl
    ): DeleteProductByIdUseCase

    @Binds
    @Singleton
    abstract fun bindClearBasketUseCase(
        impl: ClearBasketUseCaseImpl
    ): ClearBasketUseCase


    @Binds
    @Singleton
    abstract fun bindGetAllProductsOnceUseCase(
        impl: GetAllProductsOnceUseCaseImpl
    ): GetAllProductsOnceUseCase


    @Binds
    @Singleton
    abstract fun bindIncrementQuantityUseCase(
        impl: IncrementQuantityUseCaseImpl
    ): IncrementQuantityUseCase


    @Binds
    @Singleton
    abstract fun bindDecrementQuantityOrRemoveUseCase(
        impl: DecrementQuantityOrRemoveUseCaseImpl
    ): DecrementQuantityOrRemoveUseCase


    @Binds
    @Singleton
    abstract fun bindGetTotalQuantityFlowUseCase(
        impl: GetTotalQuantityFlowUseCaseImpl
    ): GetTotalQuantityFlowUseCase


    @Binds
    @Singleton
    abstract fun bindGetAllProductsFlowUseCase(
        impl: GetAllProductsFlowUseCaseImpl
    ): GetAllProductsFlowUseCase


    @Binds
    @Singleton
    abstract fun bindAddOrIncUseCase(
        impl: AddOrIncrementProductUseCaseImpl
    ): AddOrIncrementProductUseCase
}