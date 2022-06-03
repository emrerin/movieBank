package com.example.movies

import Movie
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.adapter.RecyclerViewAdapter
import com.example.movies.databinding.ActivityMainBinding
import com.example.movies.factory.MyViewModelFactory
import com.example.movies.repository.MainRepository
import com.example.movies.service.RetrofitService
import com.example.movies.utils.Constants
import com.example.movies.view.MDetailsActivity
import com.example.movies.viewmodels.MainViewModel

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.Listener {

    private val TAG = "üMainActivity"
    lateinit var binding : ActivityMainBinding
    lateinit var viewModel: MainViewModel

    private val retrofitService = RetrofitService.getInstance()
    var adapter = RecyclerViewAdapter(this@MainActivity)

    companion object {
        const val MOVIE = "movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        //set recyclerview adapter
        binding.recyclerView.adapter = adapter

        //get viewmodel instance using MyViewModelFactory
        viewModel =
            ViewModelProvider(this, MyViewModelFactory(MainRepository(retrofitService))).get(
                MainViewModel::class.java
            )

        viewModel.foundMovies.observe(this, Observer {
            Log.d(TAG, "movie: $it")
            adapter.setMovieList(it)
        })

        viewModel.progressBar.observe(this, Observer {
            binding.progressBar.visibility=if(it){
                View.VISIBLE}else{
                View.GONE}
        })

        viewModel.errorMessage.observe(this, Observer {
            Log.d(TAG, "ğErrorMessage: $it")
        })

        viewModel.emptyMovie.observe(this, Observer {
            it?.let {
                Constants.showAlert(context = this@MainActivity, "Sorry!", "Please write the movie you want to search.")
            }
        })

        // fetch by movie name on searchview
        binding.searchButton.setOnClickListener {
            var searched = binding.searchView.query.toString()
            if(Constants.isNetworkAvailable(context = this@MainActivity)) {
                viewModel.getAllBankData(searched)
            } else {
                Constants.showAlert(context = this@MainActivity, "Attention", "Please check your internet connection.")
            }
        }
    }

    // hide soft keyboard after clicking
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    // go to second screen movie detals
    override fun onItemClick(movie: Movie) {
        val intent = Intent(this, MDetailsActivity::class.java)
        intent.putExtra(MOVIE, movie)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

}