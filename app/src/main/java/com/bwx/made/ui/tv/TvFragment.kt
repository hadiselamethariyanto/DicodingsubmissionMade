package com.bwx.made.ui.tv

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
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

}