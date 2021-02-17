package com.armboldmind.grandmarket.di.modules

import android.content.Context
import androidx.room.Room
import com.armboldmind.grandmarket.data.database.GrandMarketDB
import com.armboldmind.grandmarket.data.database.dao.MessageDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): GrandMarketDB {
        return Room.databaseBuilder(context, GrandMarketDB::class.java, "grand_market_database").build()
    }

    @Singleton
    @Provides
    fun provideMessageDao(grandMarketDB: GrandMarketDB): MessageDao {
        return grandMarketDB.messageDao()
    }
}
