package com.bwx.made.ui.movies

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.bwx.core.data.Resource
import com.bwx.core.data.source.local.entity.MovieEntity
import com.bwx.core.domain.model.Genre
import com.bwx.made.R
import com.bwx.made.databinding.FragmentMoviesBinding
import com.bwx.made.ui.detail_movie.DetailMovieFragment.Companion.MOVIE_KEY
import com.bwx.made.ui.detail_movie.DetailMovieFragment.Companion.MOVIE_OVERVIEW
import com.bwx.made.utils.asMergedLoadStates
import kotlinx.coroutines.flow.*
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var genreAdapter: GenresAdapter
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
            setupMovieAdapter()
            setupGenresAdapter()
            initSwipeToRefresh()
        }
    }

    private fun setupGenresAdapter() {
        val linearLayoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        genreAdapter = GenresAdapter()
        genreAdapter.setOnItemClickCallback(object : GenresAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Genre) {
                viewModel.setGenre(data.id)
            }

        })
        binding.rvGenre.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = genreAdapter
        }

        viewModel.getGenreTypes().observe(viewLifecycleOwner) { res ->
            when (res) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    res.data?.let { genreAdapter.updateData(it) }
                }
                is Resource.Error -> {

                }
            }
        }
    }

    private fun setupMovieAdapter() {
        moviesAdapter = MoviesAdapter()
        moviesAdapter.setOnItemClickCallback(object : MoviesAdapter.OnItemClickCallback {
            override fun onItemClicked(data: MovieEntity) {
                val bundle = bundleOf(MOVIE_KEY to data.id, MOVIE_OVERVIEW to data.overview)
                findNavController().navigate(R.id.action_movie_to_detail_movie, bundle)
            }

        })

        binding.rvMovies.adapter = moviesAdapter.withLoadStateHeaderAndFooter(
            header = MoviesLoadStateAdapter(moviesAdapter),
            footer = MoviesLoadStateAdapter(moviesAdapter)
        )

        viewModel.setGenre(0)

        lifecycleScope.launchWhenCreated {
            viewModel.movies.collectLatest {
                moviesAdapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            moviesAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.refresh.isRefreshing =
                    loadStates.mediator?.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {
            moviesAdapter.loadStateFlow
                .asMergedLoadStates()
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


}