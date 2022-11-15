package com.ruiz.emovie.presentation.fragments.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ruiz.emovie.R
import com.ruiz.emovie.databinding.FragmentHomeBinding
import com.ruiz.emovie.domain.model.MovieTopRated
import com.ruiz.emovie.presentation.adapters.LoaderAdapter
import com.ruiz.emovie.presentation.adapters.RecomendacionesAdapter
import com.ruiz.emovie.presentation.adapters.TopRatedMoviesAdapter
import com.ruiz.emovie.presentation.adapters.UpComingMoviesAdapter
import com.ruiz.emovie.presentation.common.BaseFragment
import com.ruiz.emovie.util.constants.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagingApi::class)
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    //Adapters
    lateinit var adapterTop: TopRatedMoviesAdapter
    lateinit var upcomingAdapter: UpComingMoviesAdapter
    lateinit var recomendacionesAdapter: RecomendacionesAdapter

    override fun FragmentHomeBinding.initialize() {
        binding = this
        initRecyclerViewUpComingMovies()
        initRecyclerViewTopRatedMovies()
        initRecyclerViewRecomendaciones()
        configSwipe()

        callData()
    }

    private fun initRecyclerViewTopRatedMovies() {
        binding.recyclerViewTopRated.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapterTop = TopRatedMoviesAdapter { movie ->
                val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                    movieId = movie?.id?.toInt() ?: 0, typeMovie = Constants.TYPE_MOVIE_TOP
                )
                findNavController().navigate(action)
            }
            adapter = adapterTop.withLoadStateHeaderAndFooter(
                footer = LoaderAdapter(),
                header = LoaderAdapter()
            )

            handleTopRatedResult()

        }
    }

    private fun initRecyclerViewUpComingMovies() {
        binding.recyclerViewUpcoming.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            upcomingAdapter = UpComingMoviesAdapter { movie ->
                val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                    movieId = movie?.id?.toInt() ?: 0, typeMovie = Constants.TYPE_MOVIE_UPCOMING
                )
                findNavController().navigate(action)
            }

            adapter = upcomingAdapter.withLoadStateHeaderAndFooter(
                footer = LoaderAdapter(),
                header = LoaderAdapter()
            )

            handleUpComingResult()

        }
    }

    private fun configSwipe() {
        binding.swipe.setOnRefreshListener {
            handleUpComingResult()
            callUpComingMovies()
            handleTopRatedResult()
            callTopRatedMovies()
        }
    }

    private fun handleUpComingResult() {

        lifecycleScope.launch {

            upcomingAdapter.loadStateFlow.collectLatest { state ->

                val error = when {
                    state.refresh is LoadState.Error -> state.refresh as LoadState.Error
                    state.prepend is LoadState.Error -> state.prepend as LoadState.Error
                    state.append is LoadState.Error -> state.append as LoadState.Error
                    else -> null
                }

                when {

                    state.refresh is LoadState.Loading -> {
                        binding.loaderItemUpcomingMovies.root.visibility = View.VISIBLE
                        binding.noDataItemUpcomingMovies.root.visibility = View.GONE
                    }

                    error != null -> {
                        binding.swipe.isRefreshing = false
                        binding.loaderItemUpcomingMovies.root.visibility = View.GONE
                        binding.noDataItemUpcomingMovies.root.visibility = View.VISIBLE
                        binding.noDataItemUpcomingMovies.tvNoData.text =
                            error.error.localizedMessage
                    }

                    upcomingAdapter.itemCount < 1 -> {
                        binding.swipe.isRefreshing = false
                        binding.loaderItemUpcomingMovies.root.visibility = View.GONE
                        binding.noDataItemUpcomingMovies.root.visibility = View.VISIBLE
                        binding.noDataItemUpcomingMovies.tvNoData.text = getString(R.string.no_info)
                    }

                    else -> {
                        binding.swipe.isRefreshing = false
                        binding.rootScroll.visibility = View.VISIBLE
                        binding.noDataItemUpcomingMovies.root.visibility = View.GONE
                        binding.loaderItemUpcomingMovies.root.visibility = View.GONE
                    }

                }

            }

        }
    }

    private fun handleTopRatedResult() {

        lifecycleScope.launch {

            adapterTop.loadStateFlow.collectLatest { state ->

                val error = when {
                    state.refresh is LoadState.Error -> state.refresh as LoadState.Error
                    state.prepend is LoadState.Error -> state.prepend as LoadState.Error
                    state.append is LoadState.Error -> state.append as LoadState.Error
                    else -> null
                }

                when {

                    state.refresh is LoadState.Loading -> {
                        binding.loaderItemTopratedMovies.root.visibility = View.VISIBLE
                        binding.noDataTopratedMovies.root.visibility = View.GONE
                    }

                    error != null -> {
                        binding.swipe.isRefreshing = false
                        binding.loaderItemTopratedMovies.root.visibility = View.GONE
                        binding.noDataTopratedMovies.root.visibility = View.VISIBLE
                        binding.noDataTopratedMovies.tvNoData.text =
                            error.error.localizedMessage

                    }

                    adapterTop.itemCount < 1 -> {
                        binding.swipe.isRefreshing = false
                        binding.loaderItemTopratedMovies.root.visibility = View.GONE
                        binding.noDataTopratedMovies.root.visibility = View.VISIBLE
                        binding.noDataTopratedMovies.tvNoData.text = getString(R.string.no_info)
                    }

                    else -> {
                        binding.swipe.isRefreshing = false
                        binding.rootScroll.visibility = View.VISIBLE
                        binding.noDataTopratedMovies.root.visibility = View.GONE
                        binding.loaderItemTopratedMovies.root.visibility = View.GONE
                    }

                }

            }

        }
    }

    private fun initRecyclerViewRecomendaciones() {

        binding.recyclerViewRecomendaciones.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            recomendacionesAdapter = RecomendacionesAdapter { item ->
                val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                    movieId = item.id.toInt(),
                    typeMovie = Constants.TYPE_MOVIE_RECOMENDATION
                )
                findNavController().navigate(action)
            }
            recomendacionesAdapter.submitList(getMoviesByKoreanLanguage().shuffled().take(6))
            adapter = recomendacionesAdapter
        }

        listenerRadioGroup()

    }

    private fun listenerRadioGroup() {

        binding.rgButton.setOnCheckedChangeListener { group, checkedId ->

            when (checkedId) {

                binding.rbCoreano.id -> {
                    recomendacionesAdapter.submitList(
                        getMoviesByKoreanLanguage().shuffled().take(6)
                    )
                }

                binding.rbLanzadas2020.id -> {
                    recomendacionesAdapter.submitList(getMoviesByYear().shuffled().take(6))
                }

            }

        }

        binding.rbCoreano.isChecked = true

    }

    private fun callData() {

        try {

            callUpComingMovies()
            callTopRatedMovies()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun callTopRatedMovies() {
        lifecycleScope.launch {
            viewModel.getAllTopRatedMovies.collectLatest {
                adapterTop.submitData(it)
                initRecyclerViewRecomendaciones()
            }
        }
    }

    private fun callUpComingMovies() {
        lifecycleScope.launch {
            viewModel.getAllUpComingMoviesUseCase.collectLatest {
                upcomingAdapter.submitData(it)
            }
        }
    }

    private fun getMoviesByYear(): List<MovieTopRated> {

        val arrayList: ArrayList<MovieTopRated> = arrayListOf()

        adapterTop.snapshot().items.forEach { movie ->
            if (movie.release_date.substring(0, 4) == "2020") {
                arrayList.add(movie)
            }
        }

        return arrayList.toList()
    }

    private fun getMoviesByKoreanLanguage(): List<MovieTopRated> {

        val arrayList: ArrayList<MovieTopRated> = arrayListOf()

        adapterTop.snapshot().items.forEach { movie ->
            if (movie.original_language == "ko") {
                arrayList.add(movie)
            }
        }

        return arrayList.toList()
    }

}