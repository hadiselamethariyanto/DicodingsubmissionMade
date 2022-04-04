package com.bwx.made.ui.detail_tv

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bwx.made.R
import com.bwx.core.domain.model.Tv
import com.bwx.made.databinding.ActivityDetailTvBinding
import com.bwx.made.databinding.ContentDetailTvBinding
import com.bwx.core.data.Resource
import com.bwx.core.domain.model.Season
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class DetailTVActivity : AppCompatActivity() {

    private lateinit var detailBinding: ContentDetailTvBinding
    private val viewmodel: DetailTvViewModel by viewModel()
    private lateinit var tv: Tv

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityDetailTvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        detailBinding = binding.detailContent

        val extras = intent.extras

        binding.fab.setOnClickListener {
            viewmodel.setFavorite()
        }

        if (extras != null) {
            val tvId = extras.getInt(EXTRA_TV)
            viewmodel.getDetailTV(tvId)
            viewmodel.getData().observe(this) { detailTV ->
                when (detailTV) {
                    is Resource.Loading -> setLoading(true, binding)
                    is Resource.Success -> {
                        if (detailTV.data != null) {
                            populateTv(detailTV.data!!, binding)
                            setLoading(false, binding)
                        }
                    }
                    is Resource.Error -> {
                        setLoading(false, binding)
                    }
                }
            }

            viewmodel.getSeasonTv(tvId).observe(this, seasonObserver)
        }
    }

    private fun setLoading(boolean: Boolean, binding: ActivityDetailTvBinding) {
        if (boolean) {
            binding.content.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.content.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun populateTv(tv: Tv, binding: ActivityDetailTvBinding) {
        with(detailBinding) {
            tvTitleTv.text = tv.name
            tvCategoryTv.text = tv.genres
            tvItemVoteAverage.text = tv.vote_average.toString()
            tvOverview.text = tv.overview
        }

        this.tv = tv

        if (tv.isFav) {
            binding.fab.imageTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red))
        } else {
            binding.fab.imageTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
        }


        Glide.with(this)
            .load(resources.getString(R.string.image_path, tv.poster_path))
            .transform(CenterCrop(), RoundedCorners(36))
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(detailBinding.imgPoster)

        Glide.with(this)
            .load(resources.getString(R.string.image_path, tv.backdrop_path))
            .centerCrop()
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_backdrop_loading)
                    .error(R.drawable.ic_error)
            )
            .into(detailBinding.imgBackdrop)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                onShareClick(this.tv)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onShareClick(tv: Tv) {
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder
            .from(this)
            .setType(mimeType)
            .setChooserTitle(resources.getString(R.string.share_description))
            .setText(resources.getString(R.string.share_text, tv.name))
            .startChooser()
    }


    private val seasonObserver = Observer<List<Season>> { seasons ->
        if (seasons != null) {
            val detailTvAdapter = DetailTvAdapter()
            detailTvAdapter.setEpisode(seasons)

            with(detailBinding.rvEpisode) {
                layoutManager = LinearLayoutManager(this@DetailTVActivity)
                setHasFixedSize(true)
                adapter = detailTvAdapter
                val dividerItemDecoration =
                    DividerItemDecoration(this@DetailTVActivity, DividerItemDecoration.VERTICAL)
                addItemDecoration(dividerItemDecoration)
            }
        }
    }

    companion object {
        const val EXTRA_TV = "extra_tv"
    }

}