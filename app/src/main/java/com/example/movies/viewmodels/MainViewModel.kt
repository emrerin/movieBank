package com.example.movies.viewmodels

import Movie
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    val foundMovies = MutableLiveData<List<Movie>>()
    var movieList: MutableList<Movie> = ArrayList()
    val errorMessage = MutableLiveData<String>()
    val progressBar = MutableLiveData<Boolean>()
    val emptyMovie = MutableLiveData<Boolean>()

    fun getAllBankData(searched: String) {
        progressBar.postValue(true)

        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getAllBankData(searched)
            if (response.isSuccessful) {
                // response type ('one movie') object -> assume recyclerview as single item
                var movie = Movie(
                    response.body()?.Title,
                    response.body()?.Genre,
                    response.body()?.Plot,
                    response.body()?.Poster
                )

                // Assuming search movie name with filtering
                movie?.let {
                    if (it.Title.isNullOrEmpty()) {
                        emptyMovie.postValue(true)
                    } else {
                        movieList.add(it)
                        foundMovies.postValue(movieList)
                    }
                }
                progressBar.postValue(false)

            } else {
                errorMessage.postValue(response.errorBody()?.string())
                progressBar.postValue(false)
            }
        }
    }
}
