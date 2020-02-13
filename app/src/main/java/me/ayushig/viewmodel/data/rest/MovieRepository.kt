package me.ayushig.viewmodel.data.rest

import androidx.lifecycle.LiveData
import io.reactivex.Single
import me.ayushig.viewmodel.BuildConfig
import me.ayushig.viewmodel.data.model.Movie
import me.ayushig.viewmodel.db.MoviesDAO
import me.ayushig.viewmodel.db.MoviesData
import javax.inject.Inject

open class MovieRepository @Inject
constructor(
    private val movieService: MovieService,
    private val moviesDAO: MoviesDAO
) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.

    val allMovies: LiveData<List<MoviesData>> = moviesDAO.getMovieItems()

    fun getPopularMovies(): Single<List<MoviesData>?> {
        return movieService.getPopularMovies(BuildConfig.TMDB_API_KEY)
            .map { it.movies }
            .map { convertInToNewList(it) }

    }

    private fun convertInToNewList(movieList: List<Movie>): List<MoviesData>? {
        val listOfMoviesData = mutableListOf<MoviesData>()
        for (movie in movieList) {
            listOfMoviesData.add(createMovieDataObject(movie))
        }
        moviesDAO.deleteAllData()
        moviesDAO.updateAll(listOfMoviesData)
        return listOfMoviesData
    }


    private fun createMovieDataObject(movie: Movie): MoviesData {
        return MoviesData(
            movie.id,
            movie.voteCount,
            movie.voteAverage,
            movie.title,
            movie.popularity,
            movie.posterPath,
            movie.backdropPath,
            movie.isAdult,
            movie.overview,
            movie.releaseDate,
            movie.isFavorite
        )

    }
}
