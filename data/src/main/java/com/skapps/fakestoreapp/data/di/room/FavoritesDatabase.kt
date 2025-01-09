package com.skapps.fakestoreapp.data.di.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.skapps.fakestoreapp.data.datasource.local.FavoritesDao
import com.skapps.fakestoreapp.data.models.favorites.FavoritesDbModel

@Database(entities = [FavoritesDbModel::class], version = 1, exportSchema = false)
abstract class FavoritesDatabase: RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}