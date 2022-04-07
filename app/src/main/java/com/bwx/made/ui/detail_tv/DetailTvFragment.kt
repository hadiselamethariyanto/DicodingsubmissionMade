package com.bwx.made.ui.detail_tv

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bwx.core.data.Resource
import com.bwx.core.data.source.local.entity.TvEntity
import com.bwx.core.domain.model.Season
import com.bwx.made.R
import com.bwx.made.databinding.ContentDetailTvBinding
import com.bwx.made.databinding.FragmentDetailTvBinding
import com.bwx.made.ui.tv.TvFragment.Companion.TV_KEY
import com.bwx.made.ui.tv.TvFragment.Companion.TV_OVERVIEW
import org.koin.android.viewmodel.ext.android.viewModel

class DetailTvFragment : Fragment() {
    private var _binding: FragmentDetailTvBinding? = null
    private val binding get() = _binding
    private lateinit var detailBinding: ContentDetailTvBinding
    private val viewModel: DetailTvViewModel by viewModel()
    private lateinit var detailTvAdapter: DetailTvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailTvBinding.inflate(layoutInflater, container, false)
        detailBinding = binding?.detailContent!!
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt(TV_KEY) ?: 0
        val overview = arguments?.getString(TV_OVERVIEW)

        getDetailTv(id)
        getSeasonTV(id)
    }

    private fun getDetailTv(tvId: Int) {
        viewModel.getDetailTV(tvId)
        viewModel.getData().observe(viewLifecycleOwner) { detailTV ->
            when (detailTV) {
                is Resource.Loading -> setLoading(true)
                is Resource.Success -> {
                    if (detailTV.data != null) {
                        populateTv(detailTV.data!!)
                        setLoading(false)
                    }
                }
                is Resource.Error -> {
                    setLoading(false)
                }
            }
        }
    }

    private fun getSeasonTV(tvId: Int) {
        detailTvAdapter = DetailTvAdapter()
        with(detailBinding.rvEpisode) {
            adapter = detailTvAdapter
            val dividerItemDecoration =
                DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }
        viewModel.getSeasonTv(tvId).observe(requireActivity(), seasonObserver)
    }

    private fun populateTv(tv: TvEntity) {
        with(detailBinding) {
            tvTitleTv.text = tv.name
            tvCategoryTv.text = tv.genres
            tvItemVoteAverage.text = tv.vote_average.toString()
            tvOverview.text = tv.overview
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

    private val seasonObserver = Observer<List<Season>> { seasons ->
        if (seasons != null) {
            detailTvAdapter.setEpisode(seasons)
        }
    }


    private fun setLoading(boolean: Boolean) {
        if (boolean) {
            binding?.content?.visibility = View.INVISIBLE
            binding?.loading?.loading?.visibility = View.VISIBLE
        } else {
            binding?.content?.visibility = View.VISIBLE
            binding?.loading?.loading?.visibility = View.GONE
        }
    }

}