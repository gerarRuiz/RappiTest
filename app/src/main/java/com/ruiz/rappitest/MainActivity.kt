package com.ruiz.rappitest

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.ruiz.rappitest.databinding.ActivityMainBinding
import com.ruiz.rappitest.presentation.adapters.LoaderAdapter
import com.ruiz.rappitest.presentation.adapters.TopRatedMoviesAdapter
import com.ruiz.rappitest.presentation.adapters.UpComingMoviesAdapter
import com.ruiz.rappitest.presentation.fragments.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}