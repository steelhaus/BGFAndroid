package com.example.boardgamefinder.data.retrofit

import com.example.boardgamefinder.domain.models.Event
import retrofit2.Response
import retrofit2.http.GET

/**
 * API for app network operations
 */
internal interface BoardGameFinderApi {
    // ToDo remove example
    @GET("breeds/")
    suspend fun getBreeds(): Response<List<String>>
    @GET("events/")
    suspend fun getEvents(): Response<List<Event>>
}