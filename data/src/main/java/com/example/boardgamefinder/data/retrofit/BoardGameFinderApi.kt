package com.example.boardgamefinder.data.retrofit

import com.example.boardgamefinder.data.retrofit.models.CodeRequest
import com.example.boardgamefinder.data.retrofit.models.GenericResponse
import com.example.boardgamefinder.data.retrofit.models.UserCredentialsRequest
import com.example.boardgamefinder.domain.models.*
import retrofit2.Response
import retrofit2.http.*

/**
 * API for app network operations
 */
internal interface BoardGameFinderApi {
    @GET("events")
    suspend fun getEvents(): Response<GenericResponse<List<Event>>>

    @POST("profile")
    suspend fun register(
        @Body userCredentials: UserCredentialsRequest
    ): Response<GenericResponse<Token>>

    @PUT("profile/confirm")
    suspend fun confirmCode(
        @Header ("jwt") jwt: String,
        @Body code: CodeRequest
    ): Response<GenericResponse<Tokens>>

    @POST("auth/sign-in")
    suspend fun logIn(
        @Body userCredentials: UserCredentialsRequest
    ): Response<GenericResponse<Tokens>>

    @DELETE("auth/sign-in")
    suspend fun logOut(
        @Header ("jwt") jwt: String
    ): Response<GenericResponse<Boolean>>

    @POST("events")
    suspend fun createEvent(
        @Header ("jwt") jwt: String,
        @Body creatingEvent: CreatingEvent
    ): Response<GenericResponse<Int>>

    @GET("events/members")
    suspend fun getEventMembers(): Response<GenericResponse<List<User>>>
}