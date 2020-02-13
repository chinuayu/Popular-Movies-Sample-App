package me.ayushig.viewmodel.data.rest

import io.reactivex.Single
import me.ayushig.viewmodel.data.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String): Single<MoviesResponse>

}
