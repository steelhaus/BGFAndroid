package com.example.boardgamefinder.domain.repository

import com.example.boardgamefinder.domain.models.*

/**
 * Interface for interacting with user data
 */
interface UserRepository {
    suspend fun register(credentials: UserCredentials): Result<String>
    suspend fun logIn(email: Email, password: Password): Result<Tokens>
    suspend fun confirmCode(code: String, jwt: String): Result<Tokens>

    // ToDo move to event repository
    suspend fun getEvents(): Result<List<Event>>
    suspend fun createEvent(jwt: String, event: CreatingEvent): Result<Int>
    suspend fun getEventMembers(eventId: Int): Result<List<User>>
}