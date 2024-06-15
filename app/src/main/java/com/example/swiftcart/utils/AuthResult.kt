package com.example.swiftcart.utils

sealed interface AuthResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : AuthResult<T>
    object Loading : AuthResult<Nothing>
    data class Error<out T: Any>(val error: Throwable) : AuthResult<T>
}
