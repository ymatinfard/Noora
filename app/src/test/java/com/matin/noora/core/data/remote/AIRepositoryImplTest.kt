package com.matin.noora.core.data.remote

import com.matin.noora.core.data.AIRepositoryImpl
import com.matin.noora.core.data.local.MessageDao
import com.matin.noora.core.data.local.model.MessageEntity
import com.matin.noora.core.domain.model.Prompt
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test

class AIRepositoryImplTest {

    val testDispatcher = StandardTestDispatcher()
    val testScope = TestScope(testDispatcher)

    val nooraApi = mockk<NooraApi>()
    val messageDao = mockk<MessageDao>()
    val messageQueue = mockk<MessageQueue>()
    val repository = AIRepositoryImpl(nooraApi, messageDao, messageQueue,testScope, testDispatcher)

    @Test
    fun `insertToDb should insert message into db`() {
        testScope.runTest {
            val text = "Hello world"
            val categoryId = "noora"
            val messageSlot = slot<MessageEntity>()

            coEvery { nooraApi.sendMessage(any()) } returns mockk {
                //    every { toEntity() } returns mockk()
            }
            every { messageDao.insertMessage(capture(messageSlot)) } just Runs

            repository.insertToDb(Prompt(text), categoryId)

            assertEquals(text, messageSlot.captured.prompt)
            coVerify(exactly = 1) { messageDao.insertMessage(any()) }
        }
    }
}
