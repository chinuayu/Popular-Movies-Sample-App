package me.ayushig.viewmodel.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.ayushig.viewmodel.base.OpenForTesting
import me.ayushig.viewmodel.data.model.Movie
import me.ayushig.viewmodel.db.MoviesData
import javax.inject.Inject

@OpenForTesting
class DetailsViewModel @Inject
constructor() : ViewModel() {

    private val selectedMovie = MutableLiveData<MoviesData>()

    fun getSelectedMovie(): LiveData<MoviesData> {
        return selectedMovie
    }

    fun setSelectedMovie(movie: MoviesData?) {
        selectedMovie.value = movie
    }
}
