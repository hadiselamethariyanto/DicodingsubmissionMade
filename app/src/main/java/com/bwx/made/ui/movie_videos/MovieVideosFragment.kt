package com.bwx.made.ui.movie_videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bwx.core.data.Resource
import com.bwx.core.domain.model.Video
import com.bwx.made.databinding.FragmentMovieVideosBinding
import com.bwx.made.ui.detail_movie.DetailMovieFragment.Companion.MOVIE_KEY
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.koin.android.viewmodel.ext.android.viewModel


class MovieVideosFragment : Fragment() {

    private var _binding: FragmentMovieVideosBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieVideosViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieVideosBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt(MOVIE_KEY) ?: 0
        initVideo(id)
    }

    private fun initPlayer(key: String) {
        lifecycle.addObserver(binding.youtubePlayerView)
        binding.youtubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(key, 0f)
            }
        })
    }


    private fun initVideo(movieId: Int) {
        viewModel.getMovieVideos(movieId).observe(viewLifecycleOwner, videoObserver)

    }

    private val videoObserver = Observer<Resource<List<Video>>> { res ->
        when (res) {
            is Resource.Loading -> {

            }
            is Resource.Success -> {
                initPlayer(res.data!![0].key)
            }
            is Resource.Error -> {

            }
        }
    }
//
//    override fun onInitializationSuccess(
//        p0: YouTubePlayer.Provider?,
//        p1: YouTubePlayer?,
//        p2: Boolean
//    ) {
//        if (p1 != null) {
//            ytPlayer = p1
//        }
//    }
//
//    override fun onInitializationFailure(
//        p0: YouTubePlayer.Provider?,
//        p1: YouTubeInitializationResult?
//    ) {
//        TODO("Not yet implemented")
//    }
}