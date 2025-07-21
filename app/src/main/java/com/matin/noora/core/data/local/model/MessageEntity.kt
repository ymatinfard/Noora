package com.matin.noora.core.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.matin.noora.core.data.local.MessageStateConverter
import com.matin.noora.core.domain.model.MessageState
import com.matin.noora.core.domain.model.MessageType

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: String,
    val prompt: String,
    val response: String,
    val categoryId: String,
    val timestamp: Long,
    val type: MessageType,
    @TypeConverters(MessageStateConverter::class)
    var state: MessageState,
)
