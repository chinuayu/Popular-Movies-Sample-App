package me.ayushig.viewmodel.ui.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import me.ayushig.viewmodel.R
import me.ayushig.viewmodel.base.BaseApplication
import me.ayushig.viewmodel.base.BaseFragment
import me.ayushig.viewmodel.databinding.ScreenDetailsBinding
import me.ayushig.viewmodel.db.MoviesData
import me.ayushig.viewmodel.ui.main.MainActivity
import me.ayushig.viewmodel.ui.main.MainActivitySharedViewModel
import me.ayushig.viewmodel.util.ViewModelFactory
import javax.inject.Inject

class DetailsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var mainActivitySharedViewModel: MainActivitySharedViewModel

    private lateinit var binding: ScreenDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.screen_details, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        BaseApplication.getApplicationComponent().inject(this)

        initViewModels()

        displayMovie()
    }

    private fun initViewModels() {
        detailsViewModel = ViewModelProvider(this, viewModelFactory)
            .get(DetailsViewModel::class.java)

        mainActivitySharedViewModel =
            ViewModelProvider(activity as MainActivity, viewModelFactory)
                .get(MainActivitySharedViewModel::class.java)

        detailsViewModel.setSelectedMovie(mainActivitySharedViewModel.getSelectedMovie().value)
    }

    private fun displayMovie() {
        detailsViewModel.getSelectedMovie().observe(viewLifecycleOwner, Observer {
            updateDetailsPage(it)
        })
    }

    private fun updateDetailsPage(movie: MoviesData) {

        setToolbar(movie.title)

        binding.textTitle.text = movie.title
        binding.textReleaseDate.text = movie.releaseDate
        binding.ratingScore.rating = movie.voteAverage / 2
        binding.textSynopsis.text = movie.overview

        loadImages(R.string.tmdb_backdrop_url, movie.backdropPath, binding.imgCover)

        loadImages(R.string.tmdb_image_url, movie.posterPath, binding.imgPoster)

        updateFavState(movie)

        binding.fabAddFavorite.setOnClickListener {
            movie.isFavorite = !movie.isFavorite
            updateFavState(movie)
        }
    }

    private fun loadImages(staticImagePath: Int, posterPath: String?, imageView: ImageView?) {
        context?.let { it1 ->
            Glide.with(it1)
                .load(context!!.getString(staticImagePath, posterPath))
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.e("glide exception", e?.message!!)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(imageView!!)
        }
    }

    private fun setToolbar(title: String?) {
        title?.let { it1 ->
            (activity as MainActivity).setToolbar(
                it1,
                showHome = true
            )
        }
    }

    private fun updateFavState(it: MoviesData) {
        if (it.isFavorite) {
            binding.fabAddFavorite.setImageResource(R.drawable.ic_favorite_24dp)
        } else {
            binding.fabAddFavorite.setImageResource(R.drawable.ic_favorite_border_24dp)
        }
    }
}
