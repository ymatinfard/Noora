package com.matin.noora.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.matin.noora.core.data.local.model.MessageEntity

@Database(
    entities = [MessageEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(MessageStateConverter::class)
abstract class ChatDatabase: RoomDatabase() {
    abstract fun messageDao(): MessageDao
}