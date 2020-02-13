package me.ayushig.viewmodel.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import me.ayushig.viewmodel.R
import me.ayushig.viewmodel.base.BaseApplication
import me.ayushig.viewmodel.base.BaseFragment
import me.ayushig.viewmodel.databinding.MovieListFragmentBinding
import me.ayushig.viewmodel.db.MoviesData
import me.ayushig.viewmodel.ui.main.MainActivity
import me.ayushig.viewmodel.ui.main.MainActivitySharedViewModel
import me.ayushig.viewmodel.util.ItemDecoration
import me.ayushig.viewmodel.util.ViewModelFactory
import javax.inject.Inject

class MovieListFragment : BaseFragment(), MovieAdapter.MovieClickListener {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var movieListViewModel: MovieListViewModel
    private lateinit var mainActivitySharedViewModel: MainActivitySharedViewModel

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var binding: MovieListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.movie_list_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.loadingView.visibility = View.VISIBLE
        binding.loadingView.playAnimation()

        BaseApplication.getApplicationComponent().inject(this)

        initViewModels()

        initToolbar()

        initRecyclerViewAndAdapter()

        observableViewModel()
    }

    private fun initRecyclerViewAndAdapter() {
        context.let {
            movieAdapter = MovieAdapter(context!!, this)
            binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        }

        binding.recyclerView.addItemDecoration(
            ItemDecoration(
                resources.getDimensionPixelSize(R.dimen.photos_list_spacing),
                resources.getInteger(R.integer.photo_list_preview_columns)
            )
        )
        binding.recyclerView.adapter = movieAdapter

    }

    private fun initToolbar() {
        (activity as MainActivity).setToolbar(getString(R.string.app_name), false)
    }

    private fun initViewModels() {

        movieListViewModel =
            ViewModelProvider(this, viewModelFactory).get(MovieListViewModel::class.java)

        mainActivitySharedViewModel =
            ViewModelProvider(activity as MainActivity, viewModelFactory)
                .get(MainActivitySharedViewModel::class.java)

        movieListViewModel.fetchMovies()

    }

    private fun decideVisibility(view: View, visibility: Int) {
        view.visibility = visibility
    }

    private fun observableViewModel() {
        movieListViewModel.getMovies().observe(viewLifecycleOwner, Observer {
            if (it != null && it.isNotEmpty()) {
                binding.loadingView.cancelAnimation()
                decideVisibility(binding.loadingView, View.GONE)
                decideVisibility(binding.tvError, View.GONE)
                decideVisibility(binding.recyclerView, View.VISIBLE)
                movieAdapter.setMoviesData(it)
            } else if (binding.loadingView.visibility == View.GONE) {
                decideVisibility(binding.recyclerView, View.GONE)
                decideVisibility(binding.tvError, View.VISIBLE)
                binding.tvError.text = getString(R.string.error_message)
            }
        })

        movieListViewModel.getLoading().observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading != null) {
                binding.loadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
                binding.loadingView.playAnimation()
                if (isLoading) {
                    decideVisibility(binding.recyclerView, View.GONE)
                    decideVisibility(binding.tvError, View.GONE)
                }
            }
        })
    }

    override fun onMovieClick(movie: MoviesData) {
        mainActivitySharedViewModel.setSelectedMovie(movie)
        mainActivitySharedViewModel.setOpenDetailsFragment(true)
    }
}
