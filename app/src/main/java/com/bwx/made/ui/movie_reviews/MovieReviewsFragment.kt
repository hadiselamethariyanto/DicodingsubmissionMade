package com.bwx.made.ui.movie_reviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.bwx.made.databinding.FragmentMovieReviewsBinding
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.viewmodel.ext.android.viewModel

private const val ARG_ID = "id"

class MovieReviewsFragment : Fragment() {
    private var movieId: Int? = 0
    private val viewModel: MovieReviewsViewModel by viewModel()
    private var _binding: FragmentMovieReviewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt(ARG_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieReviewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    private fun setupAdapter() {
        val reviewsAdapter = MovieReviewsAdapter()

        binding.rvReviews.adapter = reviewsAdapter

        movieId?.let {
            viewModel.getMovieReviews(it).observe(viewLifecycleOwner) {
                lifecycleScope.launchWhenCreated {
                    reviewsAdapter.submitData(it)
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            reviewsAdapter.loadStateFlow.collectLatest { loadStates ->
                if (loadStates.source.refresh is LoadState.NotLoading && loadStates.append.endOfPaginationReached && reviewsAdapter.itemCount < 1) {
                    binding.rvReviews.isVisible = false
                    binding.imgEmpty.isVisible = true
                } else {
                    binding.rvReviews.isVisible = true
                    binding.imgEmpty.isVisible = false
                }

            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            MovieReviewsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID, param1)
                }
            }
    }
}