package com.example.boardgamefinder.domain.usecase

import com.example.boardgamefinder.domain.repository.UserRepository

/**
 * Use case for user registration
 *
 * @property userRepository repository to send user credentials
 */
class LeaveEventUseCase(
    private val userRepository: UserRepository
) {
    suspend fun execute(jwt: String, eventId: Int): Result<Unit> {
        return userRepository.leaveEvent(jwt, eventId)
    }
}