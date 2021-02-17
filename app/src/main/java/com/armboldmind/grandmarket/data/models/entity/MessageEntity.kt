package com.armboldmind.grandmarket.data.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(@PrimaryKey(autoGenerate = true) var id: Int = 0)
