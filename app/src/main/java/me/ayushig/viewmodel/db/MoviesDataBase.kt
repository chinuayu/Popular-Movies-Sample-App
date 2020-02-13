package me.ayushig.viewmodel.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MoviesData::class], version = 1, exportSchema = false)
abstract class MoviesDataBase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDAO

}