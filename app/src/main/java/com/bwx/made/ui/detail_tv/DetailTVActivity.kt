package com.bwx.made.ui.detail_tv

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bwx.made.R
import com.bwx.made.core.data.source.remote.response.DetailTVResponse
import com.bwx.made.core.domain.model.Tv
import com.bwx.made.databinding.ActivityDetailTvBinding
import com.bwx.made.databinding.ContentDetailTvBinding
import com.bwx.made.core.data.Resource
import org.koin.android.viewmodel.ext.android.viewModel

class DetailTVActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_TV = "extra_tv"
    }

    private lateinit var detailBinding: ContentDetailTvBinding
    private val viewmodel: DetailTvViewModel by viewModel()
    private lateinit var tv: DetailTVResponse

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
            viewmodel.getData().observe(this, { detailTV ->
                when (detailTV) {
                    is Resource.Loading -> setLoading(true, binding)
                    is Resource.Success -> {
                        if (detailTV.data != null) {
                            populateTv(detailTV.data, binding)
                            setLoading(false, binding)
                        }
                    }
                    is Resource.Error -> {
                        setLoading(false, binding)
                    }
                }
            })
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
            tvReleasedateTv.text = tv.first_air_date
            tvEpisodeTv.text = tv.number_of_seasons.toString()
            tvOverview.text = tv.overview
        }

        if (tv.isFav) {
            binding.fab.imageTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red))
        } else {
            binding.fab.imageTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
        }

//        val detailTvAdapter = DetailTvAdapter()
//        detailTvAdapter.setEpisode(tv.seasons)
//
//        with(detailBinding.rvEpisode) {
//            layoutManager = LinearLayoutManager(this@DetailTVActivity)
//            setHasFixedSize(true)
//            adapter = detailTvAdapter
//            val dividerItemDecoration =
//                DividerItemDecoration(this@DetailTVActivity, DividerItemDecoration.VERTICAL)
//            addItemDecoration(dividerItemDecoration)
//        }

        Glide.with(this)
            .load(resources.getString(R.string.image_path, tv.backdrop_path))
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
                onShareClick(this.tv)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onShareClick(tv: DetailTVResponse) {
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder
            .from(this)
            .setType(mimeType)
            .setChooserTitle(resources.getString(R.string.share_description))
            .setText(resources.getString(R.string.share_text, tv.name))
            .startChooser()
    }
}