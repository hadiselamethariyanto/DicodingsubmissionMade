package com.bwx.made.ui.movies

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bwx.made.R
import com.bwx.core.domain.model.Movie
import com.bwx.made.databinding.FragmentMoviesBinding
import com.bwx.core.utils.SortUtils.MOVIE_NEW
import com.bwx.core.utils.SortUtils.MOVIE_OLD
import com.bwx.core.utils.SortUtils.RANDOM
import com.bwx.core.data.Resource
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
            moviesAdapter = MoviesAdapter()

            viewModel.getListMovies(MOVIE_NEW).observe(viewLifecycleOwner, movieObserver)


            with(binding.rvMovies) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = moviesAdapter
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        activity?.menuInflater?.inflate(R.menu.menu_short, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sort = ""
        when (item.itemId) {
            R.id.action_latest -> sort = MOVIE_NEW
            R.id.action_older -> sort = MOVIE_OLD
            R.id.action_random -> sort = RANDOM
        }

        viewModel.getListMovies(sort).observe(viewLifecycleOwner, movieObserver)
        item.isChecked = true

        return super.onOptionsItemSelected(item)
    }

    private fun setLoading(boolean: Boolean) {
        binding.refresh.isRefreshing = boolean
    }

    private val movieObserver = Observer<Resource<List<Movie>>> { movies ->
        if (movies != null) {
            when (movies) {
                is Resource.Loading -> setLoading(true)
                is Resource.Success -> {
                    setLoading(false)
                    movies.data?.let { moviesAdapter.updateData(it) }
                }
                is Resource.Error -> {
                    setLoading(false)
                }
            }
        }
    }

}