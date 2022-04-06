package com.example.boardgamefinder.data.retrofit

import com.example.boardgamefinder.data.retrofit.models.CodeRequest
import com.example.boardgamefinder.data.retrofit.models.GenericResponse
import com.example.boardgamefinder.data.retrofit.models.UserCredentialsRequest
import com.example.boardgamefinder.domain.models.*
import com.example.boardgamefinder.domain.repository.UserRepository
import kotlinx.coroutines.*
import retrofit2.Response

/**
 * Implementation for user repository to provide operations with user data
 */
class UserRepository : UserRepository {
    // ToDo ask for error codes
    override suspend fun register(credentials: UserCredentials): Result<String> {
        var result: Result<String>?

        withContext(Dispatchers.IO) {
            val response: Response<GenericResponse<Token>>

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
                Result.success(response.body()!!.result.token)
        }

        return result!!
    }

    override suspend fun logIn(email: Email, password: Password): Result<Tokens> {
        var result: Result<Tokens>?

        withContext(Dispatchers.IO) {
            val response: Response<GenericResponse<Tokens>>

            try {
                response = ApiClient.instance.logIn(
                    UserCredentialsRequest(email, password)
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

    override suspend fun confirmCode(code: String, jwt: String): Result<Tokens>{
        var result: Result<Tokens>?

        withContext(Dispatchers.IO) {
            val response: Response<GenericResponse<Tokens>>

            try {
                response = ApiClient.instance.confirmCode(jwt, CodeRequest(code))
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