package com.armboldmind.grandmarket.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.armboldmind.grandmarket.data.database.dao.MessageDao
import com.armboldmind.grandmarket.data.models.entity.MessageEntity

@Database(entities = [MessageEntity::class], version = 1, exportSchema = false)
abstract class GrandMarketDB : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}