package me.ayushig.viewmodel.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import me.ayushig.viewmodel.base.OpenForTesting
import me.ayushig.viewmodel.data.rest.MovieRepository
import me.ayushig.viewmodel.db.MoviesData
import javax.inject.Inject

@OpenForTesting
class MovieListViewModel @Inject
constructor(private val movieRepository: MovieRepository) : ViewModel() {
    private var disposable: CompositeDisposable = CompositeDisposable()

    private var allMovies: LiveData<List<MoviesData>> = movieRepository.allMovies
    private val loading = MutableLiveData<Boolean>()

    fun getMovies(): LiveData<List<MoviesData>> {
        return allMovies
    }

    init {
        loading.value = true
        allMovies = movieRepository.allMovies
    }

    fun getLoading(): LiveData<Boolean> {
        return loading
    }

    fun fetchMovies() {
        disposable.add(
            movieRepository.getPopularMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ returnDefaultResponse() }, { returnDefaultResponse() })
        )
    }

    private fun returnDefaultResponse() {
        loading.value = false
        allMovies = movieRepository.allMovies
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
