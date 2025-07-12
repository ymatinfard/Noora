package com.matin.noora.core.data.di

import com.matin.noora.core.data.network.AIRepositoryImpl
import com.matin.noora.core.domain.AIRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindAIRepository(impl: AIRepositoryImpl): AIRepository
}