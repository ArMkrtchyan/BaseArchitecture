package com.armboldmind.grandmarket.di.modules

import com.armboldmind.grandmarket.data.IUserRepository
import com.armboldmind.grandmarket.data.database.repositories.UserRepositoryLocal
import com.armboldmind.grandmarket.di.qualifiers.Local
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryLocaleModule {

    @Binds
    @Local
    abstract fun provideUserRepositoryLocal(userRepositoryLocal: UserRepositoryLocal): IUserRepository
}