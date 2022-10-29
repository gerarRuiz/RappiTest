package com.example.rappitest.presentation.fragments.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rappitest.domain.model.MovieTopRated
import com.example.rappitest.domain.model.MovieUpcoming
import com.example.rappitest.domain.model.MoviesGenres
import com.example.rappitest.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _selectedTopRatedMovie: MutableStateFlow<MovieTopRated?> = MutableStateFlow(null)
    val selectedTopRatedMovie: MutableStateFlow<MovieTopRated?> = _selectedTopRatedMovie

    private val _selectedUpcomingMovie: MutableStateFlow<MovieUpcoming?> = MutableStateFlow(null)
    val selectedUpcomingMovie: MutableStateFlow<MovieUpcoming?> = _selectedUpcomingMovie

    private val _genresMovies: MutableStateFlow<List<MoviesGenres>?> = MutableStateFlow(null)
    val genresMovies: MutableStateFlow<List<MoviesGenres>?> = _genresMovies

    fun getSelectedMovieTopRated(movieId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedTopRatedMovie.value = useCases.getSelectedTopRatedMovieUseCase(movieId.toInt())
        }
    }

    fun getSelectedMovieUpComing(movieId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedUpcomingMovie.value = useCases.getSelectedUpComingMovieUseCase(movieId.toInt())
        }
    }

    fun getAllGenresMovies(ids: List<Int>){
        viewModelScope.launch(Dispatchers.IO) {
            _genresMovies.value = useCases.getAllGenresMoviesUseCase(ids)
        }
    }



}