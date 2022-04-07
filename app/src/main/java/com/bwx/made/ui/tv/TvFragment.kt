package com.bwx.made.ui.tv

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.bwx.core.data.source.local.entity.TvEntity
import com.bwx.made.R
import com.bwx.made.databinding.FragmentTvBinding
import com.bwx.made.utils.asMergedLoadStates
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import org.koin.android.viewmodel.ext.android.viewModel

class TvFragment : Fragment() {

    private var _binding: FragmentTvBinding? = null
    private val binding get() = _binding!!
    private lateinit var tvAdapter: TvAdapter
    private val viewModel: TvViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            setupAdapter()
            initSwipeToRefresh()
        }
    }


    private fun setupAdapter() {
        tvAdapter = TvAdapter()
        tvAdapter.setOnItemClickCallback(object : TvAdapter.OnItemClickCallback {
            override fun onItemClicked(data: TvEntity) {
                val bundle = bundleOf(TV_KEY to data.tv_id, TV_OVERVIEW to data.overview)
                findNavController().navigate(R.id.action_tvFragment_to_tvDetailFragment, bundle)
            }
        })

        binding.rvTv.adapter = tvAdapter.withLoadStateHeaderAndFooter(
            header = TvLoadStateAdapter(tvAdapter),
            footer = TvLoadStateAdapter(tvAdapter)
        )

        viewModel.getPagingPopularTv().observe(viewLifecycleOwner) {
            lifecycleScope.launchWhenCreated {
                tvAdapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            tvAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.refresh.isRefreshing = loadStates.mediator?.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {
            tvAdapter.loadStateFlow
                .asMergedLoadStates()
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect {
                    binding.rvTv.scrollToPosition(0)
                }
        }

    }

    private fun initSwipeToRefresh() {
        binding.refresh.setOnRefreshListener {
            tvAdapter.refresh()
        }
    }

    companion object {
        const val TV_KEY = "tv_key"
        const val TV_OVERVIEW = "tv_overview"
    }

}