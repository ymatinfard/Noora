package com.matin.noora.core.data.remote

import com.matin.noora.core.data.di.IoDispatcher
import com.matin.noora.core.data.local.MessageDao
import com.matin.noora.core.data.local.model.MessageEntity
import com.matin.noora.core.domain.model.MessageState
import com.matin.noora.core.domain.model.toDomain
import com.matin.noora.core.domain.model.toNetwork
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext


class MessageQueue @Inject constructor(
    private val genAIApi: GenAIApi,
    private val messageDao: MessageDao,
    private val appScope: CoroutineScope,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
) {

    private val messageQueue =
        Channel<String>(capacity = BUFFERED, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val semaphore = Semaphore(7)

    init {
        appScope.launch {
            for (msgId in messageQueue) {
                semaphore.withPermit {
                    val messageEntity = messageDao.getMessageById(msgId)
                    if (messageEntity != null)
                        sendToServer(messageEntity)
                }
            }
        }
    }

    fun enqueue(messageId: String) {
        messageQueue.trySend(messageId)
    }

    private suspend fun sendToServer(message: MessageEntity) = withContext(ioDispatcher) {
        try {
            val response = genAIApi.sendMessage(message.toDomain().toNetwork())

            updateMessageState(message.id, response.text,MessageState.RECEIVED)
        } catch (e: Exception) {
            try {
                updateMessageState(message.id, response = "", MessageState.FAILED)
            } catch (e: Exception) {
                //           Log.e("MessageRepository", "Failed to update message state in DB")
            }
        }
    }

    private fun updateMessageState(messageId: String, response: String, newState: MessageState) {
        messageDao.updateMessageState(messageId, response, newState)
    }
}