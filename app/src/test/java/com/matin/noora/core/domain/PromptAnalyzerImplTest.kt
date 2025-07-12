package com.matin.noora.core.domain

import com.matin.noora.core.domain.model.PromptCategory
import com.matin.noora.core.domain.model.PromptRequest
import org.junit.Assert.*
import org.junit.Test

class PromptAnalyzerImplTest {

    @Test
    fun `create prompt request`() {
        val promptAnalyzer = PromptAnalyzerImpl()
        val rawPrompt = "  This is a test prompt  "
        val category = PromptCategory.GENERAL

        val promptRequest = promptAnalyzer.createPromptRequest(rawPrompt, category)

        val expectedPromptRequest = PromptRequest("This is a test prompt", category)

        assertEquals(expectedPromptRequest, promptRequest)
    }
}