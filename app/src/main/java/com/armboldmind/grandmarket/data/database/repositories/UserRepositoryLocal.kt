package com.armboldmind.grandmarket.data.database.repositories

import android.util.Log
import com.armboldmind.grandmarket.data.IUserRepository
import javax.inject.Inject

class UserRepositoryLocal @Inject constructor() : IUserRepository {
    override suspend fun signIn() {
        Log.i("RepoTag", "Local")
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