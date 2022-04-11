package com.example.boardgamefinder.domain.usecase

import com.example.boardgamefinder.domain.models.Event
import com.example.boardgamefinder.domain.models.User
import com.example.boardgamefinder.domain.repository.UserRepository

class GetEventMembersUseCase(
    private val userRepository: UserRepository
) {
    suspend fun execute(eventId: Int): Result<List<User>> {
        return userRepository.getEventMembers(eventId)
    }
}