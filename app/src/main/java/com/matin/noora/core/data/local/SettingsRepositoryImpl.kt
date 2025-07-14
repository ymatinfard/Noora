package com.matin.noora.core.data.local

import com.matin.noora.core.domain.repository.SettingsRepository
import com.matin.noora.core.domain.model.UserScore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(): SettingsRepository {
    override fun getUserScore(): Flow<UserScore> {
        return flowOf(
            UserScore(
                streak = 10,
                like = 120,
                badge = 5
            )
        )
    }
}