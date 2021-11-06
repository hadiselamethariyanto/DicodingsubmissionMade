package com.bwx.made.ui.detail_tv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bwx.core.domain.model.Season
import com.bwx.made.R
import com.bwx.made.databinding.ItemsEpisodeBinding
import java.util.ArrayList

class DetailTvAdapter : RecyclerView.Adapter<DetailTvAdapter.DetailTvViewHolder>() {

    private var listEpisode = ArrayList<Season>()

    fun setEpisode(newEpisode: List<Season>?) {
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
        fun bind(episode: Season) {
            with(binding) {
                tvTitleEpisode.text = episode.name

                Glide.with(itemView.context)
                    .load(itemView.resources.getString(R.string.image_path, episode.poster_path))
                    .transform(CenterCrop(), RoundedCorners(36))
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgPoster)
            }
        }
    }

}