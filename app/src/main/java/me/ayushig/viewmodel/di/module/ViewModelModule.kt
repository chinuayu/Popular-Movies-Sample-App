package me.ayushig.viewmodel.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import me.ayushig.viewmodel.di.util.ViewModelKey
import me.ayushig.viewmodel.ui.detail.DetailsViewModel
import me.ayushig.viewmodel.ui.list.MovieListViewModel
import me.ayushig.viewmodel.ui.main.MainActivitySharedViewModel
import me.ayushig.viewmodel.util.ViewModelFactory
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

    @Binds
    @Singleton
    @IntoMap
    @ViewModelKey(MovieListViewModel::class)
    abstract fun bindListViewModel(movieListViewModel: MovieListViewModel): ViewModel

    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindDetailsViewModel(detailsViewModel: DetailsViewModel): ViewModel


    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(MainActivitySharedViewModel::class)
    abstract fun bindMainActivitySharedViewModel(mainActivitySharedViewModel: MainActivitySharedViewModel): ViewModel

    @Binds
    @Singleton
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
