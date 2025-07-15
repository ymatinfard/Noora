package com.matin.noora.core.data.remote

import com.matin.noora.R
import com.matin.noora.core.common.Result
import com.matin.noora.core.data.di.IoDispatcher
import com.matin.noora.core.domain.model.ChatCharacterItem
import com.matin.noora.core.domain.model.PromptRequest
import com.matin.noora.core.domain.model.TextAIResponse
import com.matin.noora.core.domain.model.Tool
import com.matin.noora.core.domain.repository.AIRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AIRepositoryImpl @Inject constructor(
    private val genAIApi: GenAIApi,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher
) : AIRepository {
    override suspend fun getTextResponse(prompt: PromptRequest): Result<TextAIResponse> =
        withContext(ioDispatcher) {
            try {
                val networkPrompt = PromptNetwork(
                    prompt = prompt.rawPrompt,
                    category = prompt.category.name,
                    userId = "123",
                    sessionId = "321"
                )
                val result = genAIApi.getTextAIResponse(networkPrompt)
                Result.Success(result.toDomain())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override fun getChatCharacters(): Flow<List<ChatCharacterItem>> = flow {
      //  val result = genAIApi.getCharacters()
        val result = listOf<CharacterNetwork>(
            CharacterNetwork(
                id = "A1",
                "majid",
                "You can talk with your trainer!"
            ),
            CharacterNetwork(
                id = "A2",
                name = "shiva",
                description = "Chat with me to teach you how to code like a professional"
            ),
            CharacterNetwork(
                id = "A2",
                name = "fatemeh",
                description = "Chat with me to teach you how to code like a professional"
            )
        )
        val characters = result.map { it.toDomain() }
        emit(characters)
    }.flowOn(
        ioDispatcher
    )

    override fun getTools(): Flow<List<Tool>> {
        return flow {
            // Simulating a network call to fetch tools
            val tools = listOf(
                Tool(id = "tool1", name = "Image creation", description = "Let me draw whatever you want. Just tell me", imgRes = R.drawable.ic_drawing,),
                Tool(id = "tool2", name = "Summarize", description = "Drop your file. I will summarize for you. No worries!", imgRes = R.drawable.ic_summarize),
                Tool(id = "tool3", name = "Write", description = "Write about what ever comes in your mind", imgRes = R.drawable.ic_writing),
                Tool(id = "tool3", name = "Math", description = "Do all math like prof", imgRes = R.drawable.ic_math),
            )
            emit(tools)
        }.flowOn(ioDispatcher)
    }
}