package com.bwx.made.ui.detail_movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bwx.made.R
import com.bwx.core.domain.model.Cast
import com.bwx.made.databinding.ItemsCastBinding

class CastAdapter : RecyclerView.Adapter<CastAdapter.CastViewHolder>() {
    private var list = ArrayList<Cast>()

    fun updateData(new: List<Cast>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val binding = ItemsCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val cast = list[position]
        holder.bind(cast)
    }

    class CastViewHolder(private val binding: ItemsCastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cast: Cast) {
            with(binding) {
                tvName.text = cast.name
                tvNickname.text = cast.character
            }

            Glide.with(itemView.context)
                .load(itemView.resources.getString(R.string.image_path, cast.profile_path))
                .transform(CenterCrop(), RoundedCorners(20))
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(binding.imgCast)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}