package com.example.boardgamefinder.data.retrofit

import com.example.boardgamefinder.data.retrofit.exceptions.Response500
import com.example.boardgamefinder.data.retrofit.exceptions.UnknownServerError
import com.example.boardgamefinder.domain.models.UserCredentials
import com.example.boardgamefinder.domain.repository.UserRepository
import kotlinx.coroutines.*

class UserRepository : UserRepository {
    override fun register(credentials: UserCredentials): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getBreeds(): Result<List<String>> {
        var result: Result<List<String>>?

        withContext(Dispatchers.IO) {
            val response = ApiClient.instance.getBreeds()

            if (response.isSuccessful && response.body() != null) {
                result = Result.success(response.body()!!)
                return@withContext
            }

            result = when (response.code()) {
                500 -> Result.failure(Response500())
                else -> Result.failure(UnknownServerError())
            }
        }

        return result!!
    }
}