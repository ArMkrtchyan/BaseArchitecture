package com.armboldmind.grandmarket.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.armboldmind.grandmarket.data.models.paginatonModels.PaginationRequestModel
import com.armboldmind.grandmarket.data.models.paginatonModels.PaginationResponseModel
import com.armboldmind.grandmarket.shared.managers.NetworkStatusManager
import retrofit2.Response

class PagingDataSource<T : Any>(private val model: PaginationRequestModel, private val call: suspend () -> Response<ResponseModel<PaginationResponseModel<T>>>) :
    PagingSource<Int, T>() {

    val data = ArrayList<T?>()

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        if (!NetworkStatusManager.isNetworkAvailable()) {
            return LoadResult.Error(Exception())
        }
        model.page = params.key ?: 1
        try {
            val response = call()
            if (response.isSuccessful) {
                response.body()?.data?.list?.let {
                    return LoadResult.Page<Int, T>(data = data.apply {
                        clear()
                        addAll(it)
                    } ?: arrayListOf<T>(),
                        prevKey = if (model.page == 1) null else model.page - 1,
                        nextKey = if (model.page == response.body()?.data?.pageCount) null else model.page + 1)
                } ?: return LoadResult.Error(Exception())
            } else return LoadResult.Error(Exception())
        } catch (e: Exception) {
            return LoadResult.Error(Exception())
        }
    }
}