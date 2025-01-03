package com.skapps.fakestoreapp.data.network.apiexecutor

import android.provider.SyncStateContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiExecutorImpl @Inject constructor(
) : ApiExecutor {
    override suspend fun <T> execute(
        call: suspend () -> Response<T>
    ): ApiResult<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = call()
                if (response.isSuccessful) {
                    ApiResult.Success(
                        response = response.body()
                    )
                } else {
                    if (response.code() == 401) {
                        ApiResult.Error(ApiError.Authentication(response.errorBody()))
                    } else {
                        ApiResult.Error(ApiError.Server(response.errorBody()))
                    }
                }

            } catch (ex: Exception) {
                if (ex is ConnectException || ex is UnknownHostException) {
                    ApiResult.Error(ApiError.NoInternet(ex.message))
                } else {
                    ApiResult.Error(ApiError.IO(ex.message))
                }
            }
        }
    }
}