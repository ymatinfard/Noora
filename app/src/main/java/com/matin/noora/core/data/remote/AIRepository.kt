package com.matin.noora.core.data.remote

import com.matin.noora.core.common.Result
import com.matin.noora.core.data.di.IoDispatcher
import com.matin.noora.core.domain.model.Character
import com.matin.noora.core.domain.repository.AIRepository
import com.matin.noora.core.domain.model.PromptRequest
import com.matin.noora.core.domain.model.TextAIResponse
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

    override fun getChatCharacters(): Flow<List<Character>> = flow {
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
            )
        )
        val characters = result.map { it.toDomain() }
        emit(characters)
    }.flowOn(
        ioDispatcher
    )
}