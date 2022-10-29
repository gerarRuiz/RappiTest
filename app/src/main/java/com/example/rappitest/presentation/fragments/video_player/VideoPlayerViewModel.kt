package com.example.rappitest.presentation.fragments.video_player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rappitest.domain.model.ApiResponseVideos
import com.example.rappitest.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val useCase: UseCases
) : ViewModel() {

    private val _getVideosMovie: MutableStateFlow<ApiResponseVideos?> = MutableStateFlow(null)
    val getVideosMovie: MutableStateFlow<ApiResponseVideos?> = _getVideosMovie

    fun getMovieVideos(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _getVideosMovie.value = useCase.getMoviesVideosUseCase(movieId)
        }
    }

}