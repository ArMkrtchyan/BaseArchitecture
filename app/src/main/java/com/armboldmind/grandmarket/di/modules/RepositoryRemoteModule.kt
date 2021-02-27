package com.armboldmind.grandmarket.di.modules

import com.armboldmind.grandmarket.data.IUserRepository
import com.armboldmind.grandmarket.data.network.repositories.UserRepositoryRemote
import com.armboldmind.grandmarket.di.qualifiers.Remote
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryRemoteModule {

    @Binds
    @Remote
    abstract fun provideUserRepositoryRemote(userRepositoryRemote: UserRepositoryRemote): IUserRepository
}