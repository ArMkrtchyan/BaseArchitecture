package com.example.data.dataSource

import com.example.basearchitecture.shared.PreferencesManager
import com.example.basearchitecture.shared.exceptions.BadRequestException
import com.example.basearchitecture.shared.exceptions.InternalServerErrorException
import com.example.basearchitecture.shared.exceptions.NetworkException
import com.example.data.mappers.IMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class DataSource @Inject constructor(
    private val mPreferencesManager: PreferencesManager,
) : IDataSource {

    override suspend fun <T, K> getResult(mapper: IMapper<T, K>?, call: suspend () -> ResponseResult<T>
    ): Flow<K> {
        return flow {
            val result = call()
            if (result.isSuccessful) {
                emit(result.body()?.result?.let { mapper?.map(it) } ?: throw InternalServerErrorException(result.message()))
            } else {
                if (result.code() == 401) {
                }
                if (result.code() == 404) throw InternalServerErrorException(result.message())
                if (result.code() in 400..499 && result.code() != 401 && result.code() != 404) throw BadRequestException(result.message())
                else if (result.code() in 500..599) throw InternalServerErrorException(result.message())
            }
        }.catch {
            throw if (it is SocketTimeoutException || it is UnknownHostException) NetworkException(it.localizedMessage ?: it.message ?: "") else it
        }.flowOn(Dispatchers.Default)
    }
}