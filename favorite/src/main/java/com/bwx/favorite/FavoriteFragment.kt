package com.bwx.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bwx.favorite.databinding.FragmentFavoriteBinding
import com.bwx.favorite.di.favoriteViewModelModule
import com.bwx.made.R
import com.bwx.favorite.movie.FavoriteMovieFragment
import com.bwx.favorite.tv.FavoriteTvFragment
import com.bwx.made.ui.home.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {
    private var _favoriteFragmentBinding: FragmentFavoriteBinding? = null
    private val binding get() = _favoriteFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _favoriteFragmentBinding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            loadKoinModules(favoriteViewModelModule)
            setViewpager()
        }
    }

    private fun setViewpager() {
        val fragmentList = listOf(FavoriteMovieFragment(), FavoriteTvFragment())
        val tabTitle =
            listOf(resources.getString(R.string.movies), resources.getString(R.string.tv))

        binding?.viewpager?.adapter =
            SectionsPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)

        TabLayoutMediator(binding!!.tabs, binding!!.viewpager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _favoriteFragmentBinding = null
    }

}