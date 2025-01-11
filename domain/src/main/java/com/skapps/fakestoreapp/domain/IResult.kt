package com.skapps.fakestoreapp.domain

sealed class IResult<out T,out E> {
    class Success<T>(val response: T) : IResult<T, Nothing>()
    class Error<E>(val error: UiError<E>) : IResult<Nothing, E>()

    inline fun <R> mapSuccess(transform: (T) -> R): IResult<R, E> {
        return when (this) {
            is Success -> Success(transform(response))
            is Error -> Error(error)
        }
    }
}


inline fun <T, E, R> IResult<T, E>.toDomain(crossinline transform: (T) -> R): IResult<R, E> {
    return mapSuccess { transform(it) }
}

inline fun <T, E> IResult<T, E>.onSuccess(action: (T) -> Unit): IResult<T, E> {
    if (this is IResult.Success) action(response)
    return this
}

inline fun <T, E> IResult<T, E>.onError(action: (UiError<E>) -> Unit): IResult<T, E> {
    if (this is IResult.Error) action(error)
    return this
}
