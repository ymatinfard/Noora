package com.matin.noora.core.data

import android.util.Log
import androidx.collection.LruCache
import com.matin.noora.R
import com.matin.noora.core.data.di.IoDispatcher
import com.matin.noora.core.data.local.MessageDao
import com.matin.noora.core.data.remote.CharacterNetwork
import com.matin.noora.core.data.remote.MessageQueue
import com.matin.noora.core.data.remote.NooraApi
import com.matin.noora.core.data.remote.model.MessageRequestNetwork
import com.matin.noora.core.domain.model.ChatCharacterItem
import com.matin.noora.core.domain.model.Message
import com.matin.noora.core.domain.model.MessageAuthor
import com.matin.noora.core.domain.model.MessageState
import com.matin.noora.core.domain.model.Tool
import com.matin.noora.core.domain.model.toDomain
import com.matin.noora.core.domain.model.toEntity
import com.matin.noora.core.domain.repository.AIRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class AIRepositoryImpl @Inject constructor(
    private val nooraApi: NooraApi,
    private val messageDao: MessageDao,
    private val messageQueue: MessageQueue,
    private val appScope: CoroutineScope,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher
): AIRepository {

    private val cache = LruCache<String, List<Message>>(maxSize = 200 * 1024)

    override fun getLastMessageSnapshot(categoryId: String) = cache[categoryId]

    override fun chatWarmUp(categoryId: String) {
        appScope.launch {
            getChatMessages(categoryId).first()
        }
    }

    override fun sendMessage(message: Message) {
        appScope.launch {
            try {
                insertToDb(message.copy(state = MessageState.PENDING))

                val response = sendToServer(message)

                if (response != null) {
                    updateMessageState(message.id, MessageState.DONE)

                    insertToDb(response.copy(state = MessageState.DONE))
                } else {
                    updateMessageState(message.id, MessageState.FAILED)
                }
            } catch (e: Exception) {
                updateMessageState(message.id, MessageState.FAILED)
            }
        }
    }

    private suspend fun insertToDb(message: Message) = withContext(ioDispatcher) {
        try {
            messageDao.insertMessage(
                message.toEntity()
            )
        } catch (e: Exception) {
            Log.e("Repository", "Failed to insert to db msg id: ${message.id}")
        }
    }

    private fun updateMessageState(messageId: String, newState: MessageState) {
        try {
            messageDao.updateMessageState(messageId, newState)
        } catch (e: Exception) {
            Log.e("Repository", "Failed to update message id: ${messageId}")
        }
    }

    override fun isMessagePending(): Flow<Boolean> = messageDao.hasPendingMessages()

    private suspend fun sendToServer(message: Message): Message? =
        withContext(ioDispatcher) {
            try {
                val networkRequest =
                    MessageRequestNetwork(text = message.text, categoryId = message.categoryId)
                // val response =   nooraApi.sendMessage(networkRequest)
                delay(1000)

                // Fake message to test
                Message(
                    text = "Server response to msg",
                    author = MessageAuthor.Server,
                    categoryId = message.categoryId
                )
            } catch (e: Exception) {
                null
            }
        }

    override fun getChatMessages(categoryId: String): Flow<List<Message>> {
        return messageDao.getAllMessages(categoryId).distinctUntilChanged()
            .map { entityList -> entityList.map { it.toDomain() } }
            .onEach {
                cache.put(categoryId, it)
            }.onStart {
                emit(cache[categoryId] ?: emptyList())
            }
    }

    override fun getChatCharacters(): Flow<List<ChatCharacterItem>> = flow {
        //  val result = genAIApi.getCharacters()
        val result = fakeCharacters()
        val characters = result.map { it.toDomain() }
        emit(characters)
    }.flowOn(
        ioDispatcher
    )

    override fun getTools(): Flow<List<Tool>> {
        return flow {
            val tools = fakeTools()
            emit(tools)
        }.flowOn(ioDispatcher)
    }

    override fun getChatRecentHistory(): Flow<List<ChatCharacterItem>> {
        return flowOf(
            fakeChatCharacterItems()
        )
    }
}

@OptIn(ExperimentalTime::class)
private fun fakeChatCharacterItems(): List<ChatCharacterItem> = listOf(
    ChatCharacterItem(
        name = "Noora",
        description = "Hello, how can I assist you today?",
        timestamp = Instant.fromEpochMilliseconds(167300000000L), // Example timestamp
    ),
    ChatCharacterItem(
        name = "marketing",
        description = "What would you like to know?",
        timestamp = Instant.fromEpochMilliseconds(167300100000L), // Example timestamp
    ),
    ChatCharacterItem(
        name = "adult",
        description = "I can help you with that.",
        timestamp = Instant.fromEpochMilliseconds(167300200000L), // Example timestamp
    )
)

private fun fakeTools(): List<Tool> = listOf(
    Tool(
        id = "tool1",
        name = "Image creation",
        description = "Let me draw whatever you want. Just tell me",
        imgRes = R.drawable.ic_drawing,
    ),
    Tool(
        id = "tool2",
        name = "Summarize",
        description = "Drop your file. I will summarize for you. No worries!",
        imgRes = R.drawable.ic_summarize
    ),
    Tool(
        id = "tool3",
        name = "Write",
        description = "Write about what ever comes in your mind",
        imgRes = R.drawable.ic_writing
    ),
    Tool(
        id = "tool3",
        name = "Math",
        description = "Do all math like prof",
        imgRes = R.drawable.ic_math
    ),
)

private fun fakeCharacters(): List<CharacterNetwork> = listOf<CharacterNetwork>(
    CharacterNetwork(
        id = "A1",
        "teacher",
        "You can talk with your trainer!"
    ),
    CharacterNetwork(
        id = "A2",
        name = "trainer",
        description = "Chat with me to teach you how to code like a professional"
    ),
    CharacterNetwork(
        id = "A2",
        name = "legal",
        description = "Chat with me to teach you how to code like a professional"
    )
)