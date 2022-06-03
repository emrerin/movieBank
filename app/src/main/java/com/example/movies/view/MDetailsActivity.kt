package com.example.movies.view

import Movie
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movies.MainActivity
import com.example.movies.R
import com.example.movies.databinding.ActivityMdetailsBinding
import com.squareup.picasso.Picasso

class MDetailsActivity : AppCompatActivity() {

    lateinit var binding : ActivityMdetailsBinding
    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMdetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // actionbar
        val actionbar = supportActionBar

        intent?.let {
            movie = intent.extras?.getParcelable(MainActivity.MOVIE)
            actionbar?.title.let {
                actionbar?.title = movie?.Title
            }

            // Even if the answer returns code:200, the value (N/A) for the movies without pictures
            if (movie?.Poster.equals("N/A")) {
                binding.imgMovieDetails.setImageResource(R.drawable.no_img_available)
            } else {
                Picasso.get()
                    .load(movie?.Poster)
                    .into(binding.imgMovieDetails)
            }
            binding.themeMovieDetails.text = movie?.Genre
            binding.descMovieDetails.text = movie?.Plot
        }

        // set back button
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }

    // navigate previous screen
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
        return true
    }
}