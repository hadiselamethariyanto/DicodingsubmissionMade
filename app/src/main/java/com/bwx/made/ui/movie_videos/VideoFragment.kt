package com.bwx.made.ui.movie_videos

import android.os.Bundle
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment

class VideoFragment : YouTubePlayerFragment(), YouTubePlayer.OnInitializedListener {
    private lateinit var player: YouTubePlayer
    private var videoId: String? = "Y_UmWdcTrrc"

    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)
        initialize("AIzaSyD7LvudK7Pm3kxFYZICXnmKBUa2DFr5Syo", this)
    }

    fun setVideoId(videoId: String) {
        player.cueVideo(videoId)
    }

    override fun onDestroy() {
        player.release()
        super.onDestroy()
    }

    override fun onPause() {
        player.pause()
        super.onPause()
    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        if (p1 != null) {
            this.player = p1
        }
        player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT)
//        player.setOnFullscreenListener(activity as VideoListDemoActivity)
        if (!p2) {
            player.cueVideo(videoId)
        }
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {

    }

    companion object {
        const val ARG_KEY_VIDEO = "key_video"
    }
}