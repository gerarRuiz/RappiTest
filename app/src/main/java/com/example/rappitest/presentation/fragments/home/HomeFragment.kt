package com.example.rappitest.presentation.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rappitest.R
import com.example.rappitest.databinding.FragmentHomeBinding
import com.example.rappitest.domain.model.MovieTopRated
import com.example.rappitest.presentation.adapters.LoaderAdapter
import com.example.rappitest.presentation.adapters.RecomendacionesAdapter
import com.example.rappitest.presentation.adapters.TopRatedMoviesAdapter
import com.example.rappitest.presentation.adapters.UpComingMoviesAdapter
import com.example.rappitest.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    //Adapters
    lateinit var adapterTop: TopRatedMoviesAdapter
    lateinit var upcomingAdapter: UpComingMoviesAdapter
    lateinit var recomendacionesAdapter: RecomendacionesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerViewUpComingMovies()
        initRecyclerViewTopRatedMovies()

        callData()

    }

    private fun initRecyclerViewTopRatedMovies() {
        binding.recyclerViewTopRated.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapterTop = TopRatedMoviesAdapter { movie ->
                val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(movieId = movie?.id?.toInt() ?: 0, typeMovie = Constants.TYPE_MOVIE_TOP)
                findNavController().navigate(action)
            }
            adapter = adapterTop.withLoadStateHeaderAndFooter(
                footer = LoaderAdapter(),
                header = LoaderAdapter()
            )

        }
    }

    private fun initRecyclerViewUpComingMovies() {
        binding.recyclerViewUpcoming.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            upcomingAdapter = UpComingMoviesAdapter { movie ->
                val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(movieId = movie?.id?.toInt() ?: 0, typeMovie = Constants.TYPE_MOVIE_UPCOMING)
                findNavController().navigate(action)
            }
            adapter = upcomingAdapter.withLoadStateHeaderAndFooter(
                footer = LoaderAdapter(),
                header = LoaderAdapter()
            )
        }
    }

    private fun initRecyclerViewRecomendaciones() {

        binding.recyclerViewRecomendaciones.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            recomendacionesAdapter = RecomendacionesAdapter { item ->
                val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(movieId = item.id.toInt(), typeMovie = Constants.TYPE_MOVIE_RECOMENDATION)
                findNavController().navigate(action)
            }
            recomendacionesAdapter.submitList(getMoviesByKoreanLanguage().take(6))
            adapter = recomendacionesAdapter
        }

        binding.rbCoreano.isChecked = true

        listenerRadioGroup()

    }

    private fun listenerRadioGroup() {

        binding.rgButton.setOnCheckedChangeListener { group, checkedId ->

            when (checkedId) {

                binding.rbCoreano.id -> {
                    recomendacionesAdapter.submitList(getMoviesByKoreanLanguage().take(6))
                }

                binding.rbLanzadas2020.id -> {
                    recomendacionesAdapter.submitList(getMoviesByYear().take(6))
                }

            }

        }


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