package com.bwx.made.ui.tv

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bwx.made.R
import com.bwx.made.core.domain.model.Tv
import com.bwx.made.databinding.FragmentTvBinding
import com.bwx.made.utils.SortUtils.RANDOM
import com.bwx.made.utils.SortUtils.TV_NEW
import com.bwx.made.utils.SortUtils.TV_OLD
import com.bwx.made.viewmodel.ViewModelFactory
import com.bwx.made.vo.Resource

class TvFragment : Fragment() {

    private var _binding: FragmentTvBinding? = null
    private val binding get() = _binding!!
    private lateinit var tvAdapter: TvAdapter
    private lateinit var viewModel: TvViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(
                requireActivity(),
                factory
            )[TvViewModel::class.java]

            tvAdapter = TvAdapter()

            viewModel.getListTv(TV_NEW).observe(viewLifecycleOwner, tvObserver)

            with(binding.rvTv) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvAdapter
            }

            binding.refresh.setOnRefreshListener {
                setLoading(true)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        activity?.menuInflater?.inflate(R.menu.menu_short, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sort = ""
        when (item.itemId) {
            R.id.action_latest -> sort = TV_NEW
            R.id.action_older -> sort = TV_OLD
            R.id.action_random -> sort = RANDOM
        }

        viewModel.getListTv(sort).observe(viewLifecycleOwner, tvObserver)
        item.isChecked = true

        return super.onOptionsItemSelected(item)
    }

    private fun setLoading(boolean: Boolean) {
        binding.refresh.isRefreshing = boolean
        if (boolean) {
            binding.rvTv.visibility = View.GONE
        } else {
            binding.rvTv.visibility = View.VISIBLE
        }
    }

    private val tvObserver = Observer<Resource<List<Tv>>> { tv ->
        if (tv != null) {
            when (tv) {
                is Resource.Loading -> setLoading(true)
                is Resource.Success -> {
                    setLoading(false)
                    tv.data?.let { tvAdapter.updateData(it) }
                }
                is Resource.Error -> {
                    setLoading(false)
                }
            }
        }
    }

}