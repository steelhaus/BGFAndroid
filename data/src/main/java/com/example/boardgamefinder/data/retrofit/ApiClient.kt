package com.example.boardgamefinder.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Client for network
 */
internal object ApiClient {
    private const val BASE_URL = "https://bgf.ij.je/"
    private const val VERSION = "v1.0/"

    val instance: BoardGameFinderApi by lazy{
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL + VERSION)
            .build()
        retrofit.create(BoardGameFinderApi::class.java)
    }
}