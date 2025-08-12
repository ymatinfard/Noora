import com.matin.noora.core.data.local.MessageDao
import com.matin.noora.core.data.local.model.MessageEntity
import com.matin.noora.core.data.remote.NooraApi
import com.matin.noora.core.data.remote.MessageQueue
import com.matin.noora.core.data.remote.TextAINetwork
import com.matin.noora.core.domain.model.Message
import com.matin.noora.core.domain.model.MessageState
import com.matin.noora.core.domain.model.MessageType
import com.matin.noora.core.domain.model.Prompt
import com.matin.noora.core.domain.model.toNetwork
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class)
class MessageQueueTest {

    private lateinit var nooraApi: NooraApi
    private lateinit var messageDao: MessageDao
    private lateinit var messageQueue: MessageQueue
    private lateinit var testScope: TestScope
    private lateinit var testDispatcher: TestDispatcher

    @Before
    fun setUp() {
        nooraApi = mockk()
        messageDao = mockk()
        testDispatcher = StandardTestDispatcher()
        testScope = TestScope(testDispatcher)

        messageQueue = MessageQueue(
            genAIApi = nooraApi,
            messageDao = messageDao,
            appScope = testScope,
            ioDispatcher = testDispatcher
        )
    }

    @After
    fun tearDown() {
        testScope.cancel()
        clearAllMocks()
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `enqueue should fetch message and send to server`() = runTest(testDispatcher) {
        val messageId = "test-id"
        val messageEntity = MessageEntity(
            id = messageId,
            prompt = "Hello AI",
            response = "",
            categoryId = "cat1",
            timestamp = java.time.Instant.now().toEpochMilli(),
            type = MessageType.TEXT,
            state = MessageState.PENDING
        )

        val domainMessage = Message(
            id = messageId,
            prompt = Prompt("Hello AI"),
            response = "",
            categoryId = "cat1",
            createdAt = messageEntity.timestamp,
            state = MessageState.PENDING
        )

        val networkRequest = domainMessage.toNetwork()
        val apiResponse = TextAINetwork(text = "Hi Human!", "noora")

        every { messageDao.updateMessageState(any(), any(), any()) } just Runs
        coEvery { messageDao.getMessageById(messageId) } returns messageEntity
        coEvery { nooraApi.sendMessage(networkRequest) } returns apiResponse

        messageQueue.enqueue(messageId)
        advanceUntilIdle()

        coVerify(exactly = 1) { messageDao.getMessageById(any()) }
        coVerify(exactly = 1) { nooraApi.sendMessage(any()) }
        verify(exactly = 1) {
            messageDao.updateMessageState(any(), any(), any())
        }
    }
}
