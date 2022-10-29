package com.example.rappitest.data.paging_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.rappitest.data.local.MoviesDatabase
import com.example.rappitest.data.remote.MoviesApi
import com.example.rappitest.domain.model.MovieUpcoming
import com.example.rappitest.domain.model.MovieUpcomingKeys

@ExperimentalPagingApi
class MoviesUpComingMediator(
    private val moviesApi: MoviesApi,
    private val moviesDatabase: MoviesDatabase
) : RemoteMediator<Int, MovieUpcoming>() {

    private val upcomingDao = moviesDatabase.moviesUpcomingDao()
    private val upcomingKeysDao = moviesDatabase.moviesUpcomingKeysDao()

    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val lastUpdated =
            upcomingKeysDao.getAllMovieUpcomingRemoteKeys()?.firstOrNull()?.lastUpdated ?: 0L
        val cacheTimeout = 5

        val diferienciaEnMinutos = (currentTime - lastUpdated) / 1000 / 60

        return if (diferienciaEnMinutos.toInt() <= cacheTimeout) {

            InitializeAction.SKIP_INITIAL_REFRESH

        } else {

            InitializeAction.LAUNCH_INITIAL_REFRESH

        }

    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieUpcoming>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeysCercanasPosicionActual(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeysPorPrimeraVez(state)
                    val prevPage = remoteKeys?.previousPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeysPorUltimaVez(state)
                    val nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = false
                    )
                    nextPage
                }
            }

            val response = moviesApi.getUpComingMovies(page = page)
            if (response.results?.isNotEmpty() == true) {
                moviesDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        upcomingDao.deleteAllMoviesUpcoming()
                        upcomingKeysDao.deleteAllMoviesUpcomingRemoteKeys()
                    }
                    val prevPage = response.page
                    val nextPage = prevPage + 1
                    val lastUpdated = System.currentTimeMillis()
                    val keys = response.results.map { movieUpComing ->
                        MovieUpcomingKeys(
                            movieUpComing.id,
                            prevPage,
                            nextPage,
                            lastUpdated
                        )
                    }
                    upcomingKeysDao.addAllMoviesUpcomingRemoteKeys(keys)
                    upcomingDao.addMoviesUpcoming(response.results)
                }
            }

            MediatorResult.Success(endOfPaginationReached = response.page + 1 < response.totalPages!!)

        } catch (e: Exception) {
            e.printStackTrace()
            return MediatorResult.Error(e)
        }

    }

    private suspend fun getRemoteKeysPorPrimeraVez(state: PagingState<Int, MovieUpcoming>): MovieUpcomingKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { movie ->
            upcomingKeysDao.getMovieUpcomingRemoteKeys(movie.id)
        }
    }

    private suspend fun getRemoteKeysPorUltimaVez(state: PagingState<Int, MovieUpcoming>): MovieUpcomingKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { movie ->
            upcomingKeysDao.getMovieUpcomingRemoteKeys(movie.id)
        }
    }

    private suspend fun getRemoteKeysCercanasPosicionActual(state: PagingState<Int, MovieUpcoming>): MovieUpcomingKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                upcomingKeysDao.getMovieUpcomingRemoteKeys(id)
            }
        }
    }


}