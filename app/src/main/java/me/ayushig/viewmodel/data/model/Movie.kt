package me.ayushig.viewmodel.data.model

import com.google.gson.annotations.SerializedName

/**
 * https://developers.themoviedb.org/3/movies/get-popular-movies
 * Pojo class for parsing the data from the above source
 */
data class Movie(

    @SerializedName("vote_count")
    var voteCount: Int = 0,

    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("video")
    private var video: Boolean = false,

    @SerializedName("vote_average")
    var voteAverage: Float = 0.toFloat(),

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("popularity")
    var popularity: Float = 0.toFloat(),

    @SerializedName("poster_path")
    var posterPath: String? = null,

    @SerializedName("original_language")
    var originalLanguage: String? = null,

    @SerializedName("original_title")
    var originalTitle: String? = null,

    @SerializedName("genre_ids")
    val genreIds: List<Int>? = null,

    @SerializedName("backdrop_path")
    var backdropPath: String? = null,

    @SerializedName("adult")
    var isAdult: Boolean = false,

    @SerializedName("overview")
    var overview: String? = null,

    @SerializedName("release_date")
    var releaseDate: String? = null,

    var isFavorite: Boolean = false
)

