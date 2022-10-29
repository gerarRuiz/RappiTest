package com.ruiz.rappitest.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ruiz.rappitest.R
import com.ruiz.rappitest.databinding.MovieItemGridBinding
import com.ruiz.rappitest.domain.model.MovieTopRated
import com.ruiz.rappitest.util.Constants

class RecomendacionesAdapter(
    var callback: (item: MovieTopRated) -> Unit
) : ListAdapter<MovieTopRated, RecomendacionesAdapter.ViewHolder>(
    DiffUtilCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            MovieItemGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, callback)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: MovieItemGridBinding,
        private val callback: (item: MovieTopRated) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movieTopRated: MovieTopRated) {

            Glide.with(binding.imageView)
                .load("${Constants.BASE_URL_IMAGES}${movieTopRated.poster_path}")
                .error(R.drawable.ic_image)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imageView)

            binding.imageView.setOnClickListener { callback.invoke(movieTopRated) }
        }

    }

    class DiffUtilCallback : DiffUtil.ItemCallback<MovieTopRated>() {
        override fun areItemsTheSame(oldItem: MovieTopRated, newItem: MovieTopRated): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieTopRated, newItem: MovieTopRated): Boolean {
            return oldItem == newItem
        }
    }

}