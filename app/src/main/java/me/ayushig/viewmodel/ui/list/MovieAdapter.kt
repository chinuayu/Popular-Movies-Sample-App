package me.ayushig.viewmodel.ui.list

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import me.ayushig.viewmodel.R
import me.ayushig.viewmodel.databinding.MovieListItemBinding
import me.ayushig.viewmodel.db.MoviesData

class MovieAdapter(
    private val mContext: Context,
    private val mOnClickListener: MovieClickListener
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    lateinit var list: List<MoviesData>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = MovieListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(mContext)
            .load(mContext.getString(R.string.tmdb_image_url, list[position].posterPath))
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,Ma
                    model: Any,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.e("glide exception", e?.message)
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
            .into(holder.binding.imgPoster)
            .apply { RequestOptions.placeholderOf(R.drawable.ic_movie_black_24dp) }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setMoviesData(moviesData: List<MoviesData>) {
        list = moviesData
        notifyDataSetChanged()
    }

    // Provide a reference to the views for each data item
    inner class ViewHolder(val binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            this.binding.root.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            mOnClickListener.onMovieClick(list[adapterPosition])
        }
    }

    interface MovieClickListener {
        fun onMovieClick(movie: MoviesData)
    }

    companion object {
        private val TAG = MovieAdapter::class.java.simpleName
    }
}
