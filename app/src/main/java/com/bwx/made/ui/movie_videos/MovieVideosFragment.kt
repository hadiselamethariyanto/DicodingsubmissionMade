package com.bwx.made.ui.movie_videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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
    private lateinit var videosAdapter: VideosAdapter
    private lateinit var ytp: YouTubePlayer

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
        initPlayList()
    }

    private fun initPlayList() {
        videosAdapter = VideosAdapter()
        videosAdapter.setOnItemClickCallback(object : VideosAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Video) {
                ytp.loadVideo(data.key, 0f)
            }
        })

        binding.rvPlayList.apply {
            adapter = videosAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
        }
    }

    private fun initPlayer(key: String) {
        lifecycle.addObserver(binding.youtubePlayerView)
        binding.youtubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                ytp = youTubePlayer
                youTubePlayer.loadVideo(key, 0f)
                binding.loading.loading.visibility = View.GONE
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
                videosAdapter.updateDate(res.data!!)
            }
            is Resource.Error -> {
                Toast.makeText(requireActivity(), res.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.youtubePlayerView.release()
    }

}