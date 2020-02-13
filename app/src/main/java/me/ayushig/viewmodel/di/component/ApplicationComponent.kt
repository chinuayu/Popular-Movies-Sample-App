package me.ayushig.viewmodel.di.component

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import me.ayushig.viewmodel.base.BaseApplication
import me.ayushig.viewmodel.db.MoviesDAO
import me.ayushig.viewmodel.db.MoviesDataBase
import me.ayushig.viewmodel.di.module.ApplicationModule
import me.ayushig.viewmodel.di.module.ContextModule
import me.ayushig.viewmodel.ui.detail.DetailsFragment
import me.ayushig.viewmodel.ui.list.MovieListFragment
import me.ayushig.viewmodel.ui.main.MainActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ContextModule::class, ApplicationModule::class]
)
interface ApplicationComponent {

    fun inject(application: BaseApplication)

    fun inject(mainActivity: MainActivity)

    fun inject(movieListFragment: MovieListFragment)

    fun inject(detailsFragment: DetailsFragment)

    fun movieDao(): MoviesDAO

    fun movieDataBase(): MoviesDataBase

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}