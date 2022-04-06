package com.example.boardgamefinder.domain.usecase

import com.example.boardgamefinder.domain.models.Event
import com.example.boardgamefinder.domain.repository.UserRepository

class GetEventsUseCase(
    private val userRepository: UserRepository
) {
    suspend fun execute(): Result<List<Event>> {
        return userRepository.getEvents()
    }
}