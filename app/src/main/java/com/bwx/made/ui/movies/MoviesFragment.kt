package com.bwx.made.ui.movies

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.bwx.made.R
import com.bwx.made.databinding.FragmentMoviesBinding
import kotlinx.coroutines.flow.*
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private lateinit var moviesAdapter: MoviesAdapter
    private val viewModel: MoviesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
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
        moviesAdapter = MoviesAdapter()

        viewModel.getListMovies().observe(viewLifecycleOwner) {
            lifecycleScope.launchWhenCreated {
                moviesAdapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            moviesAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.refresh.isRefreshing =
                    loadStates.mediator?.refresh is LoadState.Loading
            }
        }

        with(binding.rvMovies) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = moviesAdapter.withLoadStateHeaderAndFooter(
                header = MoviesLoadStateAdapter(moviesAdapter),
                footer = MoviesLoadStateAdapter(moviesAdapter)
            )
        }

        lifecycleScope.launchWhenCreated {
            moviesAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect {
                    binding.rvMovies.scrollToPosition(0)
                }
        }
    }

    private fun initSwipeToRefresh() {
        binding.refresh.setOnRefreshListener {
            moviesAdapter.refresh()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        activity?.menuInflater?.inflate(R.menu.menu_short, menu)
    }

}