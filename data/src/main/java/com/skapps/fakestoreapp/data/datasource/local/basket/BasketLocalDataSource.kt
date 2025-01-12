package com.skapps.fakestoreapp.data.datasource.local.basket
import com.skapps.fakestoreapp.data.models.cart.BasketProductsDbModel
import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import kotlinx.coroutines.flow.Flow

interface BasketLocalDataSource {

    /**
     * Verilen ürünü sepete ekler veya günceller (Upsert).
     */
    suspend fun upsertProduct(product: BasketProductsDbModel): IResult<BasketProductsDbModel, ApiErrorModel>

    /**
     * Verilen id'li ürünü sepetten siler.
     */
    suspend fun deleteProductById(id: Int): IResult<Int, ApiErrorModel>

    /**
     * Sepeti tamamen temizler (Tüm ürünleri siler).
     */
    suspend fun clearBasket(): IResult<Unit, ApiErrorModel>

    /**
     * Tüm sepet ürünlerini tek seferde (suspend) getirir.
     */
    suspend fun getAllProductsOnce(): IResult<List<BasketProductsDbModel>, ApiErrorModel>

    /**
     * Verilen id'ye sahip ürünü döndürür.
     */
    suspend fun getProductById(id: Int): IResult<BasketProductsDbModel?, ApiErrorModel>

    /**
     * Verilen id'li ürünün miktarını 1 arttırır.
     */
    suspend fun incrementQuantity(id: Int): IResult<Unit, ApiErrorModel>

    /**
     * Verilen id'li ürünün miktarını 1 azaltır,
     * eğer 0'a düşerse ilgili ürünü siler.
     */
    suspend fun decrementQuantityOrRemove(id: Int): IResult<Unit, ApiErrorModel>

    /**
     * Toplam ürün miktarını gerçek zamanlı olarak döner. (Flow)
     * Bu fonksiyon domain veya repository katmanında wrap’lenebilir.
     */
    fun getTotalQuantityFlow(): Flow<Int?>


    /**
     * Tüm sepet ürünlerini gerçek zamanlı olarak döner. (Flow)
     * Bu fonksiyon domain veya repository katmanında wrap’lenebilir.
     */
    fun getAllProductsFlow(): Flow<List<BasketProductsDbModel>>

    suspend fun addOrIncrementProduct(product: BasketProductsDbModel): IResult<Unit, ApiErrorModel>
}