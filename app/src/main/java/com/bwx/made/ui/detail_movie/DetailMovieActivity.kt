package com.bwx.made.ui.detail_movie

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bwx.made.R
import com.bwx.core.data.source.remote.response.DetailMovieResponse
import com.bwx.core.domain.model.Cast
import com.bwx.core.domain.model.Movie
import com.bwx.made.databinding.ActivityDetailMovieBinding
import com.bwx.made.databinding.ContentDetailMovieBinding
import com.bwx.core.data.Resource
import org.koin.android.viewmodel.ext.android.viewModel

class DetailMovieActivity : AppCompatActivity() {

    private lateinit var detailBinding: ContentDetailMovieBinding
    private val viewModel: DetailMovieViewModel by viewModel()
    private lateinit var movie: DetailMovieResponse
    private lateinit var castAdapter: CastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailBinding = binding.detailContent

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.fab.setOnClickListener {
            viewModel.setFavorite()
        }

        val extras = intent.extras
        if (extras != null) {
            val movieId = extras.getInt(EXTRA_MOVIE)
            viewModel.getDetailMovie(movieId)
            viewModel.getData().observe(this, { movie ->
                when (movie) {
                    is Resource.Loading -> setLoading(true, binding)
                    is Resource.Success -> {
                        if (movie.data != null) {
                            populateMovie(movie.data!!, binding)
                            setLoading(false, binding)
                        }
                    }
                    is Resource.Error -> {
                        setLoading(false, binding)
                    }
                }
            })

            castAdapter = CastAdapter()

            viewModel.getCastMovie(movieId).observe(this, castObserver)

            with(detailBinding.rvCast) {
                layoutManager =
                    LinearLayoutManager(
                        this@DetailMovieActivity,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                setHasFixedSize(true)
                adapter = castAdapter
            }
        }

    }

    private fun populateMovie(movie: Movie, binding: ActivityDetailMovieBinding) {
        with(detailBinding) {
            tvTitleMovie.text = movie.title
            tvCategoryMovie.text = movie.genres
            tvReleasedateMovie.text = movie.release_date
            tvDurationMovie.text = movie.runtime.toString()
            tvOverview.text = movie.overview
        }

        if (movie.isFav) {
            binding.fab.imageTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red))
        } else {
            binding.fab.imageTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
        }

        Glide.with(this)
            .load(resources.getString(R.string.image_path, movie.backdrop_path))
            .transform(RoundedCorners(20))
            .centerCrop()
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(detailBinding.imgPoster)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                onShareClick(this.movie)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onShareClick(movie: DetailMovieResponse) {
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder
            .from(this)
            .setType(mimeType)
            .setChooserTitle(resources.getString(R.string.share_description))
            .setText(resources.getString(R.string.share_text, movie.title))
            .startChooser()
    }

    private fun setLoading(boolean: Boolean, binding: ActivityDetailMovieBinding) {
        if (boolean) {
            binding.content.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.content.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }

    private val castObserver = Observer<Resource<List<Cast>>> { casts ->
        if (casts != null) {
            when (casts) {
                is Resource.Success -> {
                    detailBinding.rvCast.visibility = View.VISIBLE
                    casts.data?.let { castAdapter.updateData(it) }
                }
                is Resource.Error -> detailBinding.rvCast.visibility = View.GONE
                is Resource.Loading -> detailBinding.rvCast.visibility = View.GONE
            }
        }
    }

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }


}