package com.bwx.made.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bwx.made.databinding.ItemNetworkStateBinding

class MoviesLoadStateAdapter(private val adapter: MoviesAdapter) :
    LoadStateAdapter<MoviesLoadStateAdapter.ViewHolder>() {

    class ViewHolder(
        private val binding: ItemNetworkStateBinding,
        private val retryCallback: () -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindTo(loadState: LoadState) {
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState is LoadState.Error
            binding.retryButton.setOnClickListener {
                retryCallback()
            }
            binding.errorMsg.isVisible =
                !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
            binding.errorMsg.text = (loadState as? LoadState.Error)?.error?.message
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val binding =
            ItemNetworkStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding) {
            adapter.retry()
        }
    }
}