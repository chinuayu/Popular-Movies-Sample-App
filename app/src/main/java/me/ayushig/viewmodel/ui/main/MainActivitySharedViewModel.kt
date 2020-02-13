package me.ayushig.viewmodel.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.ayushig.viewmodel.db.MoviesData
import javax.inject.Inject

class MainActivitySharedViewModel @Inject
constructor() : ViewModel() {
    private val selectedMovie = MutableLiveData<MoviesData>()
    val openDetailsFragment = MutableLiveData<Boolean>()

    fun setOpenDetailsFragment(shouldOpen: Boolean) {
        openDetailsFragment.value = shouldOpen
    }

    fun getSelectedMovie(): LiveData<MoviesData> {
        return selectedMovie
    }

    fun setSelectedMovie(movie: MoviesData) {
        selectedMovie.value = movie
    }
}