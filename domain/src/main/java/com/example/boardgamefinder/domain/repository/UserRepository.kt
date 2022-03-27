package com.example.boardgamefinder.domain.repository

import com.example.boardgamefinder.domain.models.*

/**
 * Interface for interacting with user data
 */
interface UserRepository {
    // register user
    suspend fun register(credentials: UserCredentials): Result<String>
    suspend fun logIn(email: Email, password: Password): Result<Tokens>
    suspend fun confirmCode(code: String, jwt: String): Result<Tokens>
    // ToDo move to event repository
    suspend fun getEvents(): Result<List<Event>>
}