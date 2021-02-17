package com.armboldmind.grandmarket.di.components

import android.content.Context
import com.armboldmind.grandmarket.data.database.GrandMarketDB
import com.armboldmind.grandmarket.di.modules.AppModule
import com.armboldmind.grandmarket.di.modules.DatabaseModule
import com.armboldmind.grandmarket.di.modules.NetworkModule
import com.armboldmind.grandmarket.shared.managers.PreferencesManager
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, AppModule::class, DatabaseModule::class])
interface AppComponent {
    fun context(): Context
    fun retrofit(): Retrofit
    fun preferences(): PreferencesManager
    fun grandMarketDb(): GrandMarketDB

    val authorizationComponent: AuthorizationComponent.Builder
}