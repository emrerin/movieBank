package com.example.movies.service

import Movie
import com.example.movies.utils.Constants.BASE_URL
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    // exmpl -> base_url : http://omdbapi.com/?t=batman&apikey=26a69173&
    // ->   : ?t=batman&apikey=26a69173&

    @GET("?apikey=26a69173&")
    suspend fun getData(@Query("t") title: String): Response<Movie>

    companion object {

        var retrofitService: RetrofitService? = null

        //Create the Retrofit service instance using the retrofit.
        fun getInstance(): RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}