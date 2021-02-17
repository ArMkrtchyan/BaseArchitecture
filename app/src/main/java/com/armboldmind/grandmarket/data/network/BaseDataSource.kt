package com.armboldmind.grandmarket.data.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.armboldmind.grandmarket.data.models.paginatonModels.PaginationRequestModel
import com.armboldmind.grandmarket.data.models.paginatonModels.PaginationResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection

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

    protected suspend fun <T : Any> getPagingResult(model: PaginationRequestModel, call: suspend () -> Response<ResponseModel<PaginationResponseModel<T>>>): Flow<PagingData<T>> {
        return Pager(createDefaultPagingConfig()) {
            PagingDataSource<T>(model, call)
        }.flow
    }

    private fun createDefaultPagingConfig(): PagingConfig {
        return PagingConfig(20, 5, enablePlaceholders = false)
    }

}