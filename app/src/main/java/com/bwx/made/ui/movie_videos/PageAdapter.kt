package com.bwx.made.ui.movie_videos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.bwx.core.domain.model.Video
import com.bwx.made.R
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailLoader.OnThumbnailLoadedListener
import com.google.android.youtube.player.YouTubeThumbnailView


class PageAdapter(context: Context?, private val entries: List<Video>) :
    BaseAdapter() {
    private val entryViews: List<View>
    private val thumbnailViewToLoaderMap: MutableMap<YouTubeThumbnailView, YouTubeThumbnailLoader>
    private val inflater: LayoutInflater
    private val thumbnailListener: ThumbnailListener
    private var labelsVisible: Boolean
    fun releaseLoaders() {
        for (loader in thumbnailViewToLoaderMap.values) {
            loader.release()
        }
    }

    fun setLabelVisibility(visible: Boolean) {
        labelsVisible = visible
        for (view in entryViews) {
//            view.findViewById(R.id.text).setVisibility(if (visible) View.VISIBLE else View.GONE)
        }
    }

    override fun getCount(): Int {
        return entries.size
    }

    override fun getItem(position: Int): Video {
        return entries[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view: View? = convertView
        val entry: Video = entries[position]

        // There are three cases here
        if (view == null) {
            // 1) The view has not yet been created - we need to initialize the YouTubeThumbnailView.
            view = inflater.inflate(R.layout.video_list_item, parent, false)
            val thumbnail = view.findViewById(R.id.thumbnail) as YouTubeThumbnailView
            thumbnail.tag = entry.key
            thumbnail.initialize("", thumbnailListener)
        } else {
            val thumbnail = view.findViewById(R.id.thumbnail) as YouTubeThumbnailView
            val loader = thumbnailViewToLoaderMap[thumbnail]
            if (loader == null) {
                // 2) The view is already created, and is currently being initialized. We store the
                //    current videoId in the tag.
                thumbnail.tag = entry.key
            } else {
                // 3) The view is already created and already initialized. Simply set the right videoId
                //    on the loader.
                thumbnail.setImageResource(R.drawable.loading_thumbnail)
                loader.setVideo(entry.key)
            }
        }
        val label = view?.findViewById(R.id.text) as TextView
        label.text = entry.name
        label.visibility = if (labelsVisible) View.VISIBLE else View.GONE
        return view
    }

    private inner class ThumbnailListener : YouTubeThumbnailView.OnInitializedListener,
        OnThumbnailLoadedListener {
        override fun onInitializationSuccess(
            view: YouTubeThumbnailView, loader: YouTubeThumbnailLoader
        ) {
            loader.setOnThumbnailLoadedListener(this)
            thumbnailViewToLoaderMap[view] = loader
            view.setImageResource(R.drawable.loading_thumbnail)
            val videoId = view.tag as String
            loader.setVideo(videoId)
        }

        override fun onInitializationFailure(
            view: YouTubeThumbnailView, loader: YouTubeInitializationResult
        ) {
            view.setImageResource(R.drawable.no_thumbnail)
        }

        override fun onThumbnailLoaded(view: YouTubeThumbnailView, videoId: String) {}
        override fun onThumbnailError(
            view: YouTubeThumbnailView,
            errorReason: YouTubeThumbnailLoader.ErrorReason?
        ) {
            view.setImageResource(R.drawable.no_thumbnail)
        }
    }

    init {
        entryViews = ArrayList<View>()
        thumbnailViewToLoaderMap = HashMap()
        inflater = LayoutInflater.from(context)
        thumbnailListener = ThumbnailListener()
        labelsVisible = true
    }
}