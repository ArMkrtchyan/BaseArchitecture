package com.armboldmind.grandmarket.data

interface IUserRepository {
    suspend fun signIn()
    suspend fun signInAsGuest()
    suspend fun signUp()
    suspend fun signOut()
    suspend fun update()
    suspend fun sendEmail()
    suspend fun sendCode()
    suspend fun createNewPassword()
}