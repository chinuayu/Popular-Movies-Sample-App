package me.ayushig.viewmodel.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import me.ayushig.viewmodel.BuildConfig
import me.ayushig.viewmodel.data.model.Movie
import me.ayushig.viewmodel.data.model.MoviesResponse
import me.ayushig.viewmodel.data.rest.MovieRepository
import me.ayushig.viewmodel.data.rest.MovieService
import me.ayushig.viewmodel.db.MoviesDAO
import me.ayushig.viewmodel.db.MoviesData
import me.ayushig.viewmodel.ui.list.MovieListViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class MovieListViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var movieRepository: MovieRepository

    @Mock
    lateinit var movieService: MovieService

    @Mock
    lateinit var moviesDAO: MoviesDAO

    lateinit var movieListViewModel: MovieListViewModel

    private val testScheduler = TestScheduler()

    private var liveData = MutableLiveData<List<MoviesData>>()
    private var repoData = MutableLiveData<List<MoviesData>>()


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
            Schedulers.trampoline()
        }
        movieRepository = MovieRepository(movieService, moviesDAO)
        movieListViewModel = MovieListViewModel(movieRepository)

        Mockito.`when`(movieRepository.allMovies)
            .thenReturn(repoData.apply { value = listOf(getMovieData()) })

        Mockito.`when`(moviesDAO.getMovieItems())
            .thenReturn(liveData.apply { value = listOf(getMovieData()) })
    }

    @Test
    fun testMovieModelStateOnMoviesFetch() {
        val listOfRawMovie = listOf(getRawResponse())

        val movieRespose = MoviesResponse(1, 1, 1, listOfRawMovie)

        Mockito.`when`(movieService.getPopularMovies(BuildConfig.TMDB_API_KEY))
            .thenReturn(Single.just(movieRespose))

        assert(movieListViewModel.getLoading().value == true)

        val testObserver = movieRepository.getPopularMovies()
            .subscribeOn(testScheduler)
            .observeOn(testScheduler)
            .test()
            .assertNotTerminated()
            .assertNoErrors()
            .assertValueCount(0)

        testScheduler.advanceTimeBy(1L, TimeUnit.SECONDS)

        testObserver.assertValueCount(1)

        assert(testObserver.values()[0]?.get(0)?.title == moviesDAO.getMovieItems().value?.get(0)?.title)
    }

    @After
    fun clearThings(){

    }

    private fun getMovieData(): MoviesData {
        return MoviesData(
            id = 1,
            isFavorite = false,
            title = "Star wars",
            popularity = 1.1f,
            voteAverage = 1.2f,
            voteCount = 2
        )
    }

    private fun getRawResponse(): Movie {
        return Movie(
            id = 1,
            isFavorite = false,
            title = "Star wars",
            popularity = 1.1f,
            voteAverage = 1.2f,
            voteCount = 2
        )
    }
}