package com.bwx.made.ui.favorite.tv

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bwx.made.databinding.FragmentFavoriteTvBinding
import com.bwx.made.ui.tv.TvAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteTvFragment : Fragment() {

    private var _binding: FragmentFavoriteTvBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteTvViewModel by viewModel()
    private lateinit var tvAdapter: TvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteTvBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvAdapter = TvAdapter()

        with(binding.rvTv) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = tvAdapter
        }

        viewModel.getFavTv().observe(viewLifecycleOwner, { listFavorite ->
            if (listFavorite != null) {
                if (listFavorite.isNotEmpty()) {
                    tvAdapter.updateData(listFavorite)
                    binding.emptyData.visibility = View.GONE
                } else {
                    binding.emptyData.visibility = View.VISIBLE
                }
            }
        })

    }

}