package com.example.movies.adapter

import Movie
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.databinding.RowLayoutBinding
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(private val listener: Listener) :
    RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

    interface Listener {
        fun onItemClick(movie: Movie)
    }

    var mdataList = mutableListOf<Movie>()

    fun setMovieList(mDataList: List<Movie>) {
        this.mdataList = mDataList.toMutableList()
        notifyDataSetChanged()
    }

    class RowHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, position: Int, listener: Listener) {
            itemView.setOnClickListener {
                listener.onItemClick(movie)
            }

            binding.titleMovie.text = movie.Title
            binding.themeMovie.text = movie.Genre
            binding.descMovie.text = movie.Plot

            if (movie.Poster.equals("N/A")) {
                binding.imgMovie.setImageResource(R.drawable.no_img_available)
            } else {
                Picasso.get()
                    .load(movie.Poster)
                    .into(binding.imgMovie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(binding)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(mdataList[position], position, listener)
    }

    override fun getItemCount(): Int {
        return mdataList.size
    }
}