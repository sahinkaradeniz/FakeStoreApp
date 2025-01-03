package com.skapps.fakestoreapp.data.network.apiexecutor

import retrofit2.Response


interface ApiExecutor {
    suspend fun <T> execute(
        call: suspend () -> Response<T>
    ): ApiResult<T>
}