package com.matin.noora.core.data.remote

import com.matin.noora.core.common.Result
import com.matin.noora.core.domain.model.PromptCategory
import com.matin.noora.core.domain.model.PromptRequest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class AIRepositoryImplTest {

    val genAIApi = mockk<GenAIApi>()

    @Test
    fun `returns error when exception occurs in api call`() = runTest {
        coEvery { genAIApi.getTextAIResponse(any()) } throws Exception()
        val repository = AIRepositoryImpl(genAIApi)
        val result =
            repository.getTextResponse(PromptRequest("test prompt", PromptCategory.GENERAL))
        assert(result is Result.Error)
    }
}
