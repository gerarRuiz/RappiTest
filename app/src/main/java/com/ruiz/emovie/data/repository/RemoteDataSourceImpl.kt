package com.ruiz.emovie.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ruiz.emovie.data.local.MoviesDatabase
import com.ruiz.emovie.data.paging_source.MoviesTopRatedMediator
import com.ruiz.emovie.data.paging_source.MoviesUpComingMediator
import com.ruiz.emovie.data.remote.MoviesApi
import com.ruiz.emovie.domain.model.ApiResponseVideos
import com.ruiz.emovie.domain.model.MovieTopRated
import com.ruiz.emovie.domain.model.MovieUpcoming
import com.ruiz.emovie.domain.model.MoviesGenres
import com.ruiz.emovie.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.net.UnknownHostException

@ExperimentalPagingApi
class RemoteDataSourceImpl(
    private val moviesApi: MoviesApi,
    private val moviesDatabase: MoviesDatabase
) : RemoteDataSource {

    private val topRatedDao = moviesDatabase.moviesTopRatedDao()
    private val upcomingDao = moviesDatabase.moviesUpcomingDao()
    private val genresDao = moviesDatabase.moviesGenresDao()

    override fun getAllTopRatedMovies(): Flow<PagingData<MovieTopRated>> {
        val paginSource = { topRatedDao.getAllMoviesTopRated() }

        return Pager(
            config = PagingConfig(pageSize = 20, maxSize = 200),
            remoteMediator = MoviesTopRatedMediator(
                moviesApi, moviesDatabase
            ),
            pagingSourceFactory = paginSource
        ).flow
    }

    override fun getAllUpComingMovies(): Flow<PagingData<MovieUpcoming>> {
        val pagingSource = { upcomingDao.getAllMoviesUpcoming() }
        return Pager(
            config = PagingConfig(pageSize = 20, maxSize = 200),
            remoteMediator = MoviesUpComingMediator(
                moviesApi, moviesDatabase
            ),
            pagingSourceFactory = pagingSource
        ).flow
    }

    override suspend fun getAllGenresMovies(ids: List<Int>): List<MoviesGenres> {
        val genres: ArrayList<MoviesGenres> = arrayListOf()

        try {
            val genresList = moviesApi.getGenres().genres!!
            genresDao.deleteAllMoviesGenres()
            genresDao.addMoviesGenres(genresList)
            genres.addAll(genresDao.getSelectedMovieGenres(ids))
        } catch (e: UnknownHostException) {
            e.printStackTrace()
            genres.addAll(genresDao.getSelectedMovieGenres(ids))
        }

        return genres.toList()
    }

    override suspend fun getMoviewVideos(movieId: Int): Response<ApiResponseVideos> {
        return moviesApi.getVideos(movie_id = movieId)
    }

}