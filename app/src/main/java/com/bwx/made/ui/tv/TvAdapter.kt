package com.bwx.made.ui.tv

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bwx.made.R
import com.bwx.made.core.domain.model.Tv
import com.bwx.made.databinding.ItemsTvBinding
import com.bwx.made.ui.detail_tv.DetailTVActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
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
                var format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                var newDate: Date? = null
                try {
                    newDate = format.parse(tv.first_air_date)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                format = SimpleDateFormat("dd MMMM yy", Locale.getDefault())
                val date: String = format.format(newDate)

                tvItemTitle.text = tv.name
                tvItemDate.text = date
                tvItemVoteAverage.text = tv.vote_average.toString()
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailTVActivity::class.java)
                    intent.putExtra(DetailTVActivity.EXTRA_TV, tv.tv_id)
                    itemView.context.startActivity(intent)
                }

                Glide.with(itemView.context)
                    .load(itemView.resources.getString(R.string.image_path, tv.poster_path))
                    .centerCrop()
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