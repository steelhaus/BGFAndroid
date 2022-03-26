package com.example.boardgamefinder.data.retrofit

/**
 * Class for custom data exceptions
 */
sealed class DataException(message: String) : Exception(message){
    class InternetException : DataException("Something went wrong. Please, check the Internet connection.")

    class Response422 : DataException("Unprocessable Entity.")
    class Response500 : DataException("Server broken.")
    class UnknownServerError(code: Int) : DataException(code.toString())

    internal companion object {
        // converts server exception code into custom data exception
        fun responseCodeToException(code: Int) = when (code) {
            422 -> Response422()
            500 -> Response500()
            else -> UnknownServerError(code)
        }
    }
}
