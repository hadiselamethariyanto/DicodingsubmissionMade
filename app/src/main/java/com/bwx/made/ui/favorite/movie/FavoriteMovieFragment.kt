package com.bwx.made.ui.favorite.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bwx.made.databinding.FragmentFavoriteMovieBinding
import com.bwx.made.ui.movies.MoviesAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteMovieFragment : Fragment() {

    private var _binding: FragmentFavoriteMovieBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteMovieViewModel by viewModel()
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesAdapter = MoviesAdapter()

        with(binding.rvMovie) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = moviesAdapter
        }
        viewModel.getFavMovies().observe(viewLifecycleOwner, { listFavorite ->
            if (listFavorite != null) {
                if (listFavorite.isNotEmpty()) {
                    moviesAdapter.updateData(listFavorite)
                    binding.emptyData.visibility = View.GONE
                } else {
                    binding.emptyData.visibility = View.VISIBLE
                }
            }

        })
    }


}