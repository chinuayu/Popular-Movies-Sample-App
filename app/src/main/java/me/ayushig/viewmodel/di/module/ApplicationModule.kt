package me.ayushig.viewmodel.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import me.ayushig.viewmodel.data.rest.MovieRepository
import me.ayushig.viewmodel.data.rest.MovieService
import me.ayushig.viewmodel.db.MoviesDAO
import me.ayushig.viewmodel.db.MoviesDataBase
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class ApplicationModule {
    companion object {
        const val THE_MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/"
    }

    @Singleton
    @Provides
    internal fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(THE_MOVIE_DB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    internal fun provideRetrofitService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }

    @Singleton
    @Provides
    internal fun providesMovieDataBase(context: Context): MoviesDataBase {
        return Room.databaseBuilder(
            context,
            MoviesDataBase::class.java,
            "demo-db"
        ).build()
    }

    @Singleton
    @Provides
    internal fun providesMovieDao(moviesDataBase: MoviesDataBase): MoviesDAO {
        return moviesDataBase.moviesDao()
    }

    @Singleton
    @Provides
    internal fun moviesRepository(
        movieService: MovieService,
        moviesDAO: MoviesDAO
    ): MovieRepository {
        return MovieRepository(movieService, moviesDAO)
    }
}
