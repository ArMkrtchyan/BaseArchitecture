package com.example.data.di

import com.example.data.dataSource.DataSource
import com.example.data.dataSource.IDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    @Binds
    abstract fun bindDataSource(dataSource: DataSource): IDataSource
}