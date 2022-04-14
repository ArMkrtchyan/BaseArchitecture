package com.example.data.responseModel

data class ResponseModel<T>(val success: Boolean, val result: T?, val message: String?)
