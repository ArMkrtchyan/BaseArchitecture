package com.armboldmind.grandmarket.data.network

import androidx.annotation.Keep
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection

/**
 * Abstract Base Data source class with error handling
 */
@Keep
open class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<ResponseModel<T>>): Flow<T> {
        return flow {
            val response = call()
            if (response.isSuccessful) {
                if (response.body()?.success == true && response.body()?.data != null) emit(response.body()?.data ?: throw Exception("SomeThing went wrong"))
                else throw Exception("SomeThing went wrong")
            } else if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED || response.code() == HttpURLConnection.HTTP_FORBIDDEN) throw HttpException(response) else {
                val message = if (response.body() != null) response.body()!!.message
                else ""
                throw Exception(message)
            }
        }.flowOn(Dispatchers.IO)
    }

    protected suspend fun <T : Any> getPagingResult(call: suspend () -> Response<ResponseModel<T>>): Flow<PagingData<T>> {
        return Pager(createDefaultPagingConfig()) {
            PagingDataSource<T>(call)
        }.flow
    }

    private fun createDefaultPagingConfig(): PagingConfig {
        return PagingConfig(20, 5, false, 20)
    }

}