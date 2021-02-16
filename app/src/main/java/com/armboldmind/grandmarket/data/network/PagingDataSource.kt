package com.armboldmind.grandmarket.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection

class PagingDataSource<T : Any>(private val call: suspend () -> Response<ResponseModel<T>>) : PagingSource<Int, T>() {

    val data = ArrayList<T?>()

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val position = params.key ?: 0
        val response = call()
        if (response.isSuccessful) {
            if (response.isSuccessful) {
                if (response.body()?.success == true && response.body()?.data != null) return LoadResult.Page<Int, T>(data = data,
                    prevKey = if (position == 0) null else position - 1,
                    nextKey = if (data.isEmpty() || data.size < 20) null else position + 1)
                else throw Exception("SomeThing went wrong")
            } else if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED || response.code() == HttpURLConnection.HTTP_FORBIDDEN) throw HttpException(response) else {
                val message = if (response.body() != null) response.body()!!.message
                else ""
                throw Exception(message)
            }
        } else return LoadResult.Page(data, null, null)
    }
}