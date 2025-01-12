package com.skapps.fakestoreapp.domain.repository

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.basket.BasketProductEntity
import kotlinx.coroutines.flow.Flow

interface BasketRepository {

    /**
     * Verilen ürünü sepet veritabanına ekler/günceller.
     */
    suspend fun upsertProduct(product: BasketProductEntity): IResult<BasketProductEntity, ApiErrorModel>

    /**
     * Verilen id'li ürünü sepetten siler.
     */
    suspend fun deleteProductById(id: Int): IResult<Int, ApiErrorModel>

    /**
     * Sepetteki tüm ürünleri siler.
     */
    suspend fun clearBasket(): IResult<Unit, ApiErrorModel>

    /**
     * Tüm ürünleri tek seferde getirir.
     */
    suspend fun getAllProductsOnce(): IResult<List<BasketProductEntity>, ApiErrorModel>

    /**
     * Verilen id'li ürünü getirir.
     * Bulunamazsa null dönebilir, bu durumda IResult.Success(null) gelebilir.
     */
    suspend fun getProductById(id: Int): IResult<BasketProductEntity?, ApiErrorModel>

    /**
     * Ürünün quantity’sini 1 arttırır.
     */
    suspend fun incrementQuantity(id: Int): IResult<Unit, ApiErrorModel>

    /**
     * Ürünün quantity’sini 1 azaltır, eğer 0’a düşerse ürünü siler.
     */
    suspend fun decrementQuantityOrRemove(id: Int): IResult<Unit, ApiErrorModel>

    /**
     * Toplam quantity akışını (Flow) döndürür.
     */
    fun getTotalQuantityFlow(): Flow<Int?>

    /**
     * Sepet ürünlerini gerçek zamanlı dinlemek isterseniz
     * (örneğin listenin anlık değişimini izlemek için).
     */
    fun getAllProductsFlow(): Flow<List<BasketProductEntity>>

    /**
     * Verilen ürünü sepete ekler veya günceller (Upsert).
     */
    suspend fun addOrIncrementProduct(product: BasketProductEntity): IResult<Unit, ApiErrorModel>
}