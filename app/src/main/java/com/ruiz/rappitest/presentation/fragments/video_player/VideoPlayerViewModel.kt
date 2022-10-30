package com.ruiz.rappitest.presentation.fragments.video_player

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruiz.rappitest.domain.model.ApiResponseVideos
import com.ruiz.rappitest.domain.use_cases.UseCases
import com.ruiz.rappitest.util.extensions.asLiveData
import com.ruiz.rappitest.util.extensions.update
import com.ruiz.rappitest.util.network.Result
import com.ruiz.rappitest.util.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val useCase: UseCases
) : ViewModel() {

    private val _videosMovieState: MutableLiveData<UIState<ApiResponseVideos>?> =
        MutableLiveData(null)
    val videosMovieState = _videosMovieState.asLiveData()

    fun getMovieVideos(movieId: Int) = viewModelScope.launch {
        _videosMovieState.update { UIState.Loading(status = true) }
        val response = useCase.getMoviesVideosUseCase(movieId)
        _videosMovieState.update { UIState.Loading(status = false) }
        when (response) {
            is Result.Success -> _videosMovieState.update { UIState.Success(response.data) }
            is Result.Error -> _videosMovieState.update { UIState.Error(response.error) }
        }
        _videosMovieState.update { UIState.InitialState }
    }

}