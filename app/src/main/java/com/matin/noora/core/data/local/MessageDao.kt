package com.matin.noora.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.matin.noora.core.data.local.model.MessageEntity
import com.matin.noora.core.domain.model.MessageState
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(message: MessageEntity)

    @Query("SELECT * FROM messages WHERE categoryId = :categoryId ORDER BY timestamp DESC")
    fun getAllMessages(categoryId: String): Flow<List<MessageEntity>>

    @Query("SELECT * From messages Where id = :id")
    fun getMessageById(id: String): MessageEntity?

    @Query("UPDATE messages SET state = :newState WHERE id = :messageId")
    fun updateMessageState(messageId: String, newState: MessageState)

    @Query("DELETE FROM messages")
    fun deleteAllMessages()

    @Query("SELECT * FROM messages WHERE state = 'FAILED'")
    fun getFailedMessages(): List<MessageEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM messages WHERE state = 'PENDING')")
    fun hasPendingMessages(): Flow<Boolean>
}