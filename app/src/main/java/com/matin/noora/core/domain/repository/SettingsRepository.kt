package com.matin.noora.core.domain.repository

import com.matin.noora.core.domain.model.UserScore
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getUserScore(): Flow<UserScore>
}