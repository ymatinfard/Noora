package com.matin.noora.core.domain.usecase

import com.matin.noora.core.domain.PromptAnalyzer
import com.matin.noora.core.domain.repository.AIRepository
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GenerateTextUseCaseTest {

    val aiRepository = mockk<AIRepository>()
    val promptAnalyzer = mockk<PromptAnalyzer>()
    val generateTextUseCase = GenerateTextUseCase(aiRepository, promptAnalyzer)

    @Test
    fun `returns valid response for non-empty prompt`() = runTest {
//        val rawPrompt = Prompt(value = "Valid prompt")
//        val category = PromptCategory.GENERAL
//        val promptRequest = mockk<PromptRequest>()
//        val expectedResponse = mockk<Result<TextAIResponse>>()
//
//        every { promptAnalyzer.createPromptRequest(rawPrompt.value, category) } returns promptRequest
//        coEvery { aiRepository.getTextResponse(promptRequest) } returns expectedResponse
//
//        val result = generateTextUseCase(rawPrompt, category)
//
//        assertEquals(expectedResponse, result)
    }
}