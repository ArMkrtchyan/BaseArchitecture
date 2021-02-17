package com.armboldmind.grandmarket.data.network.services

import com.armboldmind.grandmarket.data.models.paginatonModels.PaginationRequestModel
import com.armboldmind.grandmarket.data.models.paginatonModels.PaginationResponseModel
import com.armboldmind.grandmarket.data.models.responsemodels.UserResponseModel
import com.armboldmind.grandmarket.data.network.ResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IUserService {

    @GET("todos/1")
    suspend fun signIn(): Response<ResponseModel<UserResponseModel>>

    @POST("api/Product/GetInterested")
    suspend fun getHomeInterestedProducts(@Body paginationRequestModel: PaginationRequestModel): Response<ResponseModel<PaginationResponseModel<UserResponseModel>>>
}