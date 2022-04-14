package com.example.data.dataSource

import com.example.data.mappers.IMapper
import kotlinx.coroutines.flow.Flow

interface IDataSource {
    suspend fun <T, K> getResult(mapper: IMapper<T, K>? = null, call: suspend () -> ResponseResult<T>): Flow<K>
}