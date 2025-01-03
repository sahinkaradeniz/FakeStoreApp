package com.skapps.common.components

import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.UiError

fun <T> Result<IResult<T, *>>.handleActionWithResult(
    onSuccess: (T?) -> Unit,
    onError: (UiError<*>) -> Unit
) {
    this.onSuccess { result ->
        when (result) {
            is IResult.Success -> onSuccess(result.response)
            is IResult.Error -> onError(result.error)
        }
    }.onFailure { throwable ->
        onError(UiError.IO(throwable.message.orEmpty()))
    }
}