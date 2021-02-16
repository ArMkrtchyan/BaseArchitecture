package com.armboldmind.grandmarket.di.components

import com.armboldmind.grandmarket.data.network.services.IUserService
import com.armboldmind.grandmarket.di.modules.RepositoryLocaleModule
import com.armboldmind.grandmarket.di.modules.RepositoryRemoteModule
import com.armboldmind.grandmarket.ui.MainViewModel
import dagger.Subcomponent

@Subcomponent(modules = [RepositoryRemoteModule::class, RepositoryLocaleModule::class])
interface AuthorizationComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): AuthorizationComponent
    }

    fun userService(): IUserService

    fun inject(mainViewModel: MainViewModel)
}