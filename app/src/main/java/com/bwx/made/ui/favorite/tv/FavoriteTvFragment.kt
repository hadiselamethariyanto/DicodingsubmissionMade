package com.bwx.made.ui.favorite.tv

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bwx.made.databinding.FragmentFavoriteTvBinding
import com.bwx.made.ui.tv.TvAdapter
import com.bwx.made.viewmodel.ViewModelFactory

class FavoriteTvFragment : Fragment() {

    private var _binding: FragmentFavoriteTvBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FavoriteTvViewModel
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
        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[FavoriteTvViewModel::class.java]

        tvAdapter = TvAdapter()

        with(binding.rvTv) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = tvAdapter
        }

        viewModel.getFavTv().observe(viewLifecycleOwner, { listFavorite ->
            if (listFavorite != null) {
                if (listFavorite.size != 0) {
                    tvAdapter.updateData(listFavorite)
                    binding.emptyData.visibility = View.GONE
                } else {
                    binding.emptyData.visibility = View.VISIBLE
                }
                tvAdapter.notifyDataSetChanged()
            }
        })

    }

}