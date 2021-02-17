package com.armboldmind.grandmarket.data.models.paginatonModels

class PaginationResponseModel<T> {
    val page: Int? = null
    val list: List<T>? = null
    val pageCount: Int? = null
    val itemCount: Int? = null
}