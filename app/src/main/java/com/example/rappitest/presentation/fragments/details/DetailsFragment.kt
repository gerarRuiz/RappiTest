package com.example.rappitest.presentation.fragments.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.rappitest.databinding.FragmentDetailsBinding
import com.example.rappitest.domain.model.MovieTopRated
import com.example.rappitest.domain.model.MovieUpcoming
import com.example.rappitest.presentation.adapters.GenresAdapter
import com.example.rappitest.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    private val viewModel: DetailsViewModel by viewModels()

    private val safeArgs: DetailsFragmentArgs by navArgs()

    private lateinit var genresAdapter: GenresAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listeners()
        initRecyclerViewGenders()

        when (safeArgs.typeMovie) {

            Constants.TYPE_MOVIE_UPCOMING -> executeGetSelectedUpComingMovie()

            Constants.TYPE_MOVIE_TOP, Constants.TYPE_MOVIE_RECOMENDATION -> executeGetSelectedTopRatedMovie()

        }


    }

    private fun listeners() {

        binding.imgBtnBack.setOnClickListener { findNavController().popBackStack() }

        binding.layoutInfo.btnWatchTrailer.setOnClickListener {
            val action = DetailsFragmentDirections.actionDetailsFragmentToVideoPlayerFragment(movieId = safeArgs.movieId)
            findNavController().navigate(action)
        }

    }

    private fun initRecyclerViewGenders() {

        binding.layoutInfo.recyclerViewGenres.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            genresAdapter = GenresAdapter()
            adapter = genresAdapter
        }

    }

    private fun executeGetSelectedTopRatedMovie() {

        lifecycleScope.launch {
            viewModel.getSelectedMovieTopRated(safeArgs.movieId.toLong())
            viewModel.selectedTopRatedMovie.collectLatest { value ->
                getImage(binding.rootImageView, movieTopRated = value)
                setData(movieTopRated = value)
            }

        }

    }


    private fun executeGetSelectedUpComingMovie() {
        lifecycleScope.launch {
            viewModel.getSelectedMovieUpComing(safeArgs.movieId.toLong())
            viewModel.selectedUpcomingMovie.collectLatest { value ->

                getImage(binding.rootImageView, movieUpcoming = value)
                setData(movieUpcoming = value)

            }
        }
    }


    private fun getImage(
        imgView: ImageView,
        movieTopRated: MovieTopRated? = null,
        movieUpcoming: MovieUpcoming? = null
    ) {

        val value = movieTopRated?.backdrop_path ?: movieUpcoming?.backdrop_path

        Glide.with(imgView)
            .load("${Constants.BASE_URL_IMAGES}${value}")
            .centerCrop()
            .into(imgView)

    }

    private fun setData(
        movieTopRated: MovieTopRated? = null,
        movieUpcoming: MovieUpcoming? = null
    ) {

        with(binding.layoutInfo) {

            tvMovieTitle.text = movieUpcoming?.title ?: movieTopRated?.title
            tvInfoYear.text = movieUpcoming?.release_date?.substring(0, 4)
                ?: movieTopRated?.release_date?.substring(0, 4)
            tvOriginalLanguage.text =
                movieUpcoming?.original_language ?: movieTopRated?.original_language
            tvInfoRating.text =
                movieUpcoming?.vote_average?.toString() ?: movieTopRated?.vote_average?.toString()
            tvInfoOverview.text = movieUpcoming?.overview ?: movieTopRated?.overview

        }

        executeGetGenders(movieTopRated, movieUpcoming)

    }

    private fun executeGetGenders(
        movieTopRated: MovieTopRated? = null,
        movieUpcoming: MovieUpcoming? = null
    ) {

        lifecycleScope.launch(Dispatchers.IO) {

            try {
                viewModel.getAllGenresMovies(movieTopRated?.genre_ids ?: movieUpcoming?.genre_ids!!)
                viewModel.genresMovies.collectLatest { genre ->
                    val nameGenres: ArrayList<String> = arrayListOf()
                    genre?.forEach { item ->
                        nameGenres.add(item.name)
                    }

                    genresAdapter.submitList(nameGenres)

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }


        }

    }

}