package com.armboldmind.grandmarket.data.network.repositories

import androidx.paging.PagingData
import com.armboldmind.grandmarket.data.IUserRepository
import com.armboldmind.grandmarket.data.models.paginatonModels.PaginationRequestModel
import com.armboldmind.grandmarket.data.models.responsemodels.UserResponseModel
import com.armboldmind.grandmarket.data.network.BaseDataSource
import com.armboldmind.grandmarket.data.network.services.IUserService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryRemote @Inject constructor(private val mUserService: IUserService) : BaseDataSource(), IUserRepository {

    override suspend fun signIn(): Flow<UserResponseModel?> {
        return getResult { mUserService.signIn() }
    }

    override suspend fun signInAsGuest(): Flow<PagingData<UserResponseModel>> {
        val model = PaginationRequestModel()
        return getPagingResult(model) { mUserService.getHomeInterestedProducts(model) }
    }

    override suspend fun signUp() {

    }

    override suspend fun signOut() {

    }

    override suspend fun update() {

    }

    override suspend fun sendEmail() {

    }

    override suspend fun sendCode() {

    }

    override suspend fun createNewPassword() {

    }
}