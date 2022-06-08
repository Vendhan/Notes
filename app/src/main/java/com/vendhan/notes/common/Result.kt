package com.vendhan.notes.common

sealed class Result<out R> {
    class Success<out T>(val data: T) : Result<T>()
    class Error(val message: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
    object Initial : Result<Nothing>()
}
