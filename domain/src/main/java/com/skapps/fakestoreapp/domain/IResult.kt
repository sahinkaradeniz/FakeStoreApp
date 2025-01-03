package com.skapps.fakestoreapp.domain

sealed class IResult<out T,out E> {
    class Success<T>(val response: T?) : IResult<T, Nothing>()
    class Error<E>(val error: UiError<E>) : IResult<Nothing, E>()
}