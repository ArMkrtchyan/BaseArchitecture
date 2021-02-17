package com.armboldmind.grandmarket.data.database.repositories

import android.util.Log
import androidx.paging.PagingData
import com.armboldmind.grandmarket.data.IUserRepository
import com.armboldmind.grandmarket.data.database.dao.MessageDao
import com.armboldmind.grandmarket.data.models.responsemodels.UserResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryLocal @Inject constructor(messageDao: MessageDao) : IUserRepository {
    override suspend fun signIn(): Flow<UserResponseModel?> {
        Log.i("RepoTag", "Local")
        return flow { emit(null) }
    }

    override suspend fun signInAsGuest(): Flow<PagingData<UserResponseModel>> {
        return flow { }
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