package com.skapps.fakestoreapp.data.network.apiexecutor

import okhttp3.ResponseBody


sealed class ApiResult<out T> {
    class Success<T>(val response: T?) : ApiResult<T>()
    class Error(val error: ApiError) : ApiResult<Nothing>()
}
