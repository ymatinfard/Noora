package com.matin.noora.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.matin.noora.core.data.local.ChatDatabase
import com.matin.noora.core.data.local.MessageDao
import com.matin.noora.core.domain.model.Message
import com.matin.noora.core.domain.model.MessageState
import com.matin.noora.core.domain.model.Prompt
import com.matin.noora.core.domain.model.toEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class ChatDatabaseTest {

    lateinit var messageDao: MessageDao
    lateinit var db: ChatDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db =
            Room.inMemoryDatabaseBuilder(context, ChatDatabase::class.java).allowMainThreadQueries()
                .build()
        messageDao = db.messageDao()
    }

    @Test
    fun insertMessage_should_insert_a_message_into_the_database() = runTest {
        val entity = Message(
            prompt = Prompt(
                value = "text prompt",
            ),
            response = "no content",

            ).toEntity(MessageState.PENDING)

        messageDao.insertMessage(entity)
        val result = messageDao.getAllMessages("noora").first()

        assert(result.contains(entity))
    }

    @Test
    fun getAllMessages_should_return_all_messages_from_the_database() = runTest {
        val categoryId = "noora"
        val entity1 = Message(
            prompt = Prompt(
                value = "",
            ),
            categoryId = categoryId,
            response = "content"
        ).toEntity(state = MessageState.PENDING)
        val entity2 = Message(
            prompt = Prompt(
                value = "",
            ),
            response = "content"
        ).toEntity(state = MessageState.PENDING)

        messageDao.insertMessage(entity1)
        messageDao.insertMessage(entity2)

        val result = messageDao.getAllMessages(categoryId).first()

        assert(result.contains(entity1))
        assert(result.contains(entity2))
    }

    @Test
    fun deleteMessage_should_delete_a_message_from_the_database() = runTest {
        val categoryId = "noora"
        val entity = Message(
            prompt = Prompt(
                value = "",
            ),
            categoryId = categoryId,
            response = "content"
        ).toEntity(state = MessageState.PENDING)

        messageDao.insertMessage(entity)
        messageDao.deleteAllMessages()

        val result = messageDao.getAllMessages(categoryId).first()

        assert(result.isEmpty())
    }

    @Test
    fun updateMessageState_should_update_a_message_state_in_the_database() = runTest {
        val entity = Message(
            prompt = Prompt(
                value = "",
            ),
            categoryId = "noora",
            response = "content"
        ).toEntity(state = MessageState.PENDING)

        messageDao.insertMessage(entity)
        messageDao.updateMessageState(entity.id, response = "", MessageState.SENT)
        val result = messageDao.getAllMessages("noora").first()

        assert(result.first { it.id == entity.id }.state == MessageState.SENT)
    }

    @After
    fun teardown() {
        db.close()
    }
}