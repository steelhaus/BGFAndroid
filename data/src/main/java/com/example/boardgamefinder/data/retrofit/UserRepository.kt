package com.example.boardgamefinder.data.retrofit

import com.example.boardgamefinder.data.retrofit.models.GenericResponse
import com.example.boardgamefinder.data.retrofit.models.UserCredentialsRequest
import com.example.boardgamefinder.domain.models.Event
import com.example.boardgamefinder.domain.models.UserCredentials
import com.example.boardgamefinder.domain.repository.UserRepository
import kotlinx.coroutines.*
import retrofit2.Response

/**
 * Implementation for user repository to provide operations with user data
 */
class UserRepository : UserRepository {
    override suspend fun register(credentials: UserCredentials): Result<String> {
        var result: Result<String>?

        withContext(Dispatchers.IO) {
            val response: Response<GenericResponse<String>>

            try {
                response = ApiClient.instance.register(
                    UserCredentialsRequest(credentials.email, credentials.password)
                )
            } catch (e: Exception) {
                result = Result.failure(DataException.InternetException())
                return@withContext
            }

            result = if(!response.isSuccessful || !response.body()!!.success)
                Result.failure(DataException.responseCodeToException(response.code()))
            else
                Result.success(response.body()!!.result)
        }

        return result!!
    }

    // ToDo remove example
    override suspend fun getBreeds(): Result<List<String>> {
        var result: Result<List<String>>?

        withContext(Dispatchers.IO) {
            val response: Response<List<String>>

            try {
                response = ApiClient.instance.getBreeds()
            } catch (e: Exception) {
                result = Result.failure(DataException.InternetException())
                return@withContext
            }

            result = if (response.isSuccessful && response.body() != null)
                Result.success(response.body()!!)
            else
                Result.failure(DataException.responseCodeToException(response.code()))
        }

        return result!!
    }

    override suspend fun getEvents(): Result<List<Event>> {
        var result: Result<List<Event>>?

        withContext(Dispatchers.IO) {
            val response: Response<GenericResponse<List<Event>>>

            try {
                response = ApiClient.instance.getEvents()
            } catch (e: Exception) {
                result = Result.failure(DataException.InternetException())
                return@withContext
            }

            result = if(!response.isSuccessful || !response.body()!!.success)
                //Result.failure(DataException.responseCodeToException(response.body()?.error?.code ?: response.code()))
                Result.failure(DataException.responseCodeToException(response.code()))
            else
                Result.success(response.body()!!.result)
        }

        return result!!
    }
}