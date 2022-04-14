package com.example.data.dataSource

import com.example.data.responseModel.ResponseModel
import retrofit2.Response

typealias ResponseResult<T> = Response<ResponseModel<T>>