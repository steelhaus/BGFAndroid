package com.example.boardgamefinder.data.retrofit

import retrofit2.Response
import retrofit2.http.GET

interface BoardGameFinderApi {
    @GET("breeds/")
    suspend fun getBreeds(): Response<List<String>>
}