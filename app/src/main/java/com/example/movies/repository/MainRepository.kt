package com.example.movies.repository

import com.example.movies.service.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getAllBankData(searched:String) = retrofitService.getData(searched)
}