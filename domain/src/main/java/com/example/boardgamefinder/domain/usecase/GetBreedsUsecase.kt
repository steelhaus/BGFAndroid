package com.example.boardgamefinder.domain.usecase

import com.example.boardgamefinder.domain.models.UserCredentials
import com.example.boardgamefinder.domain.repository.UserRepository
import com.example.boardgamefinder.domain.utils.PasswordValidator

class GetBreedsUsecase(
    private val userRepository: UserRepository
) {
    suspend fun execute(): Result<List<String>> {
        return userRepository.getBreeds()
    }
}