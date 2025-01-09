package com.skapps.fakestoreapp.data.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skapps.fakestoreapp.data.models.favorites.FavoritesDbModel

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProductToFavorites(song: FavoritesDbModel)

    @Delete
    suspend fun deleteProductToFavorites(song: FavoritesDbModel)


    @Query("select * from favorites_table")
    suspend fun getAllProducts():List<FavoritesDbModel>


    @Query("select * from favorites_table where :id")
    suspend fun getFavoriteProductWithId(id:Int):FavoritesDbModel

}