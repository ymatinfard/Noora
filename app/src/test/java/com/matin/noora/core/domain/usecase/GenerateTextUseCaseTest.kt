package com.matin.noora.core.domain.usecase

import com.matin.noora.core.domain.AIRepository
import com.matin.noora.core.domain.PromptAnalyzer
import com.matin.noora.core.domain.model.Prompt
import com.matin.noora.core.domain.model.PromptCategory
import com.matin.noora.core.domain.model.PromptRequest
import com.matin.noora.core.domain.model.TextAIResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class GenerateTextUseCaseTest {

    val aiRepository = mockk<AIRepository>()
    val promptAnalyzer = mockk<PromptAnalyzer>()
    val generateTextUseCase = GenerateTextUseCase(aiRepository, promptAnalyzer)

    @Test
    fun `returns valid response for non-empty prompt`() = runTest {
        val rawPrompt = Prompt(value = "Valid prompt")
        val category = PromptCategory.GENERAL
        val promptRequest = mockk<PromptRequest>()
        val expectedResponse = mockk<TextAIResponse>()

        every { promptAnalyzer.createPromptRequest(rawPrompt.value, category) } returns promptRequest
        coEvery { aiRepository.getTextResponse(promptRequest) } returns expectedResponse

        val result = generateTextUseCase(rawPrompt, category)

        assertEquals(expectedResponse, result)
    }
}