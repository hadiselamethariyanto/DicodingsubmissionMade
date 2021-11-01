package com.bwx.made.ui.detail_tv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bwx.made.core.data.source.remote.response.SeasonsItem
import com.bwx.made.databinding.ItemsEpisodeBinding
import java.util.ArrayList

class DetailTvAdapter : RecyclerView.Adapter<DetailTvAdapter.DetailTvViewHolder>() {

    private var listEpisode = ArrayList<SeasonsItem>()

    fun setEpisode(newEpisode: List<SeasonsItem>?) {
        if (newEpisode == null) return
        this.listEpisode.clear()
        this.listEpisode.addAll(newEpisode)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailTvViewHolder {
        val itemsEpisodeBinding =
            ItemsEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailTvViewHolder(itemsEpisodeBinding)
    }

    override fun onBindViewHolder(holder: DetailTvViewHolder, position: Int) {
        holder.bind(listEpisode[position])
    }

    override fun getItemCount(): Int {
        return listEpisode.size
    }

    class DetailTvViewHolder(private val binding: ItemsEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(episode: SeasonsItem) {
            with(binding) {
                tvTitleEpisode.text = episode.name
            }
        }
    }

}