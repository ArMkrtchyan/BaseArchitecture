package com.armboldmind.grandmarket.data

import androidx.paging.PagingData
import com.armboldmind.grandmarket.data.models.responsemodels.UserResponseModel
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    suspend fun signIn(): Flow<UserResponseModel?>
    suspend fun signInAsGuest(): Flow<PagingData<UserResponseModel>>
    suspend fun signUp()
    suspend fun signOut()
    suspend fun update()
    suspend fun sendEmail()
    suspend fun sendCode()
    suspend fun createNewPassword()
}