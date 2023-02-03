package com.timothy.simplebookmarktask.config

sealed class Response<T>(
    val data: T? = null,
    val message: String? = null,
    val errorMessage: String? = null
){
    class Success<T>(data: T) : Response<T>(data)
    class Error<T>(
        errorMessage: String? = null
    ) : Response<T>(
        errorMessage = errorMessage
    )
    class Loading<T>(data: T? = null) : Response<T>(data)
}