package com.bwx.made.ui.tv

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bwx.made.R
import com.bwx.core.domain.model.Tv
import com.bwx.made.databinding.ItemsTvBinding
import com.bwx.made.ui.detail_tv.DetailTVActivity
import com.bwx.made.utils.Utils
import kotlin.collections.ArrayList

class TvAdapter : RecyclerView.Adapter<TvAdapter.TvViewHolder>() {
    private var list = ArrayList<Tv>()

    fun updateData(new: List<Tv>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val itemsTvBinding =
            ItemsTvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvViewHolder(itemsTvBinding)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        val tv = list[position]
        holder.bind(tv)
    }

    class TvViewHolder(private val binding: ItemsTvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: Tv) {
            with(binding) {
                tvItemTitle.text = tv.name
                tvItemVoteAverage.text = tv.vote_average.toString()

                if (tv.first_air_date.isNotEmpty() || tv.first_air_date != "null") {
                    tvItemDate.text = Utils.formatStringDate(tv.first_air_date)
                    tvItemDate.visibility = View.VISIBLE
                } else {
                    tvItemDate.visibility = View.GONE
                }
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailTVActivity::class.java)
                    intent.putExtra(DetailTVActivity.EXTRA_TV, tv.tv_id)
                    itemView.context.startActivity(intent)
                }

                Glide.with(itemView.context)
                    .load(itemView.resources.getString(R.string.image_path, tv.poster_path))
                    .transform(CenterCrop(), RoundedCorners(36))
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgPoster)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}