package com.ruiz.rappitest.presentation.fragments.video_player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.ruiz.rappitest.databinding.FragmentVideoPlayerBinding
import com.ruiz.rappitest.util.Constants.VIDEO_SITE
import com.ruiz.rappitest.util.Constants.VIDEO_TYPE
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VideoPlayerFragment : Fragment() {

    private lateinit var binding: FragmentVideoPlayerBinding

    private val safeArgs: VideoPlayerFragmentArgs by navArgs()

    private val viewModel: VideoPlayerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycle.addObserver(binding.youtubePlayer)

        var videoKey = ""

        lifecycleScope.launch {
            viewModel.getMovieVideos(safeArgs.movieId)
            viewModel.getVideosMovie.collectLatest { response ->

                response?.results?.forEach { item ->

                    if (item.official && item.site == VIDEO_SITE && item.type == VIDEO_TYPE) {
                        videoKey = item.key
                        return@forEach
                    }

                }

                playVideo(videoKey)

            }

        }


    }

    private fun playVideo(keyVideo: String) {
        binding.youtubePlayer.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {

            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadOrCueVideo(
                    lifecycle,
                    keyVideo,
                    0.0f
                )
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.youtubePlayer.release()
    }

}