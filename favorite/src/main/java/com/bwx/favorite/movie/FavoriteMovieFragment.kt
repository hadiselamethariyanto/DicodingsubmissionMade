package com.bwx.favorite.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bwx.core.domain.model.Movie
import com.bwx.favorite.databinding.FragmentFavoriteMovieBinding
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteMovieFragment : Fragment() {

    private var _binding: FragmentFavoriteMovieBinding? = null
    private val binding get() = _binding
    private val viewModel: FavoriteMovieViewModel by viewModel()
    private lateinit var moviesAdapter: FavoriteMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteMovieBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesAdapter = FavoriteMovieAdapter()
        moviesAdapter.setOnItemClickCallback(object : FavoriteMovieAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Movie) {
                val request = NavDeepLinkRequest.Builder
                    .fromUri("android-app://com.bwx.made/detail_movie/${data.id}/${data.overview}".toUri())
                    .build()
                findNavController().navigate(request)
            }
        })

        with(binding?.rvMovie) {
            this?.layoutManager = LinearLayoutManager(context)
            this?.setHasFixedSize(true)
            this?.adapter = moviesAdapter
        }

        viewModel.getFavMovies().observe(viewLifecycleOwner) { listFavorite ->
            if (listFavorite != null) {
                if (listFavorite.isNotEmpty()) {
                    moviesAdapter.updateData(listFavorite)
                    binding?.emptyData?.visibility = View.GONE
                } else {
                    binding?.emptyData?.visibility = View.VISIBLE
                }
            }

        }
    }


}