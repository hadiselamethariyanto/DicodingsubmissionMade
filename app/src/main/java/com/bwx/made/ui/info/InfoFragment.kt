package com.bwx.made.ui.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bwx.core.data.Resource
import com.bwx.core.domain.model.Cast
import com.bwx.made.databinding.FragmentInfoBinding
import com.bwx.made.ui.detail_movie.CastAdapter
import org.koin.android.viewmodel.ext.android.viewModel

private const val ARG_ID = "id"
private const val ARG_OVERVIEW = "overview"

class InfoFragment : Fragment() {
    private var movieId: Int = 0
    private var overView: String? = null
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InfoViewModel by viewModel()
    private lateinit var castAdapter: CastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt(ARG_ID)
            overView = it.getString(ARG_OVERVIEW)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCast()
        setOverview()
    }

    private fun setOverview() {
        binding.tvOverview.text = overView
    }

    private fun getCast() {
        castAdapter = CastAdapter()
        binding.rvCast.apply {
            layoutManager =
                LinearLayoutManager(
                    requireActivity(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            setHasFixedSize(true)
            adapter = castAdapter
        }
        viewModel.getCastsMovie(movieId).observe(viewLifecycleOwner, castObserver)
    }

    private val castObserver = Observer<Resource<List<Cast>>> { casts ->
        if (casts != null) {
            when (casts) {
                is Resource.Success -> {
                    binding.rvCast.visibility = View.VISIBLE
                    casts.data?.let { castAdapter.updateData(it) }
                }
                is Resource.Error -> binding.rvCast.visibility = View.GONE
                is Resource.Loading -> binding.rvCast.visibility = View.GONE
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(movieId: Int, overView: String) =
            InfoFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID, movieId)
                    putString(ARG_OVERVIEW, overView)
                }
            }
    }
}