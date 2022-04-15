package com.example.boardgamefinder.domain.usecase

import com.example.boardgamefinder.domain.models.Event
import com.example.boardgamefinder.domain.repository.UserRepository

/**
 * Use case for user registration
 *
 * @property userRepository repository to send user credentials
 */
class GetEventUseCase(
    private val userRepository: UserRepository
) {
    suspend fun execute(jwt: String, eventId: Int): Result<Event> {
        return userRepository.getEvent(jwt, eventId)
    }
}