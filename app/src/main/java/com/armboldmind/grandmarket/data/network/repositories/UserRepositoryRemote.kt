package com.armboldmind.grandmarket.data.network.repositories

import android.util.Log
import com.armboldmind.grandmarket.data.IUserRepository
import javax.inject.Inject

class UserRepositoryRemote @Inject constructor() : IUserRepository {
    override suspend fun signIn() {
        Log.i("RepoTag", "Remote")
    }

    override suspend fun signInAsGuest() {

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