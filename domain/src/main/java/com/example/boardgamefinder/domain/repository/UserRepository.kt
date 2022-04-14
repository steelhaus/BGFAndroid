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
    suspend fun getEvents(jwt: String): Result<List<Event>>
    suspend fun createEvent(jwt: String, event: CreatingEvent): Result<Int>
    suspend fun getEventMembers(eventId: Int): Result<List<User>>
    suspend fun setLike(jwt: String, eventId: Int): Result<Unit>
    suspend fun removeLike(jwt: String, eventId: Int): Result<Unit>
    suspend fun joinEvent(jwt: String, eventId: Int): Result<Event.SubscriptionStatus>
    suspend fun leaveEvent(jwt: String, eventId: Int): Result<Unit>
    suspend fun logOut(jwt: String): Result<Boolean>
}