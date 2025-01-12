package com.skapps.fakestoreapp.data.datasource.local.favorites

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skapps.fakestoreapp.data.models.favorites.FavoritesDbModel

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProductToFavorites(model: FavoritesDbModel)

    @Query("DELETE FROM favorites_table WHERE id = :id")
    suspend fun deleteProductToFavorites(id:String)


    @Query("select * from favorites_table")
    suspend fun getAllProducts():List<FavoritesDbModel>


    @Query("select * from favorites_table where :id")
    suspend fun getFavoriteProductWithId(id:Int):FavoritesDbModel

}