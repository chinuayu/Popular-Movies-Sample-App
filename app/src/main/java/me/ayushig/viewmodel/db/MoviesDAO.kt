package me.ayushig.viewmodel.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.ayushig.viewmodel.base.OpenForTesting

/**
 * Data access Object for movie db
 */
@Dao
@OpenForTesting
interface MoviesDAO {

    @Query("""SELECT * FROM MoviesList""")
    fun getMovieItems(): LiveData<List<MoviesData>>

    @Query("""SELECT * FROM MoviesList""")
    fun getRawMovieItems(): List<MoviesData>

    @Insert
    fun insertMovie(data: MoviesData)

    @Query("delete from MoviesList")
    fun deleteAllData()

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(items: List<MoviesData>)
}