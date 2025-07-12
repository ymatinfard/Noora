package com.matin.noora.core.domain.di

import com.matin.noora.core.domain.PromptAnalyzer
import com.matin.noora.core.domain.PromptAnalyzerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    @Singleton
    fun bindPromptAnalyzer(impl: PromptAnalyzerImpl): PromptAnalyzer
}