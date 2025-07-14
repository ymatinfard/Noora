package com.matin.noora.core.data.di

import com.matin.noora.core.data.local.ChatLocalRepositoryImpl
import com.matin.noora.core.data.local.SettingsRepositoryImpl
import com.matin.noora.core.data.remote.AIRepositoryImpl
import com.matin.noora.core.data.remote.GenAIApi
import com.matin.noora.core.domain.repository.AIRepository
import com.matin.noora.core.domain.repository.ChatLocalRepository
import com.matin.noora.core.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataBindModule {

    @Binds
    @Singleton
    fun bindAIRepository(impl: AIRepositoryImpl): AIRepository

    @Binds
    @Singleton
    fun bindChatLocalRepository(impl: ChatLocalRepositoryImpl): ChatLocalRepository

    @Binds
    @Singleton
    fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DataProviderModule {

    @Provides
    @Singleton
    fun providesGenAIApi(): GenAIApi {
        return Retrofit.Builder()
            .baseUrl("https://api.google.example.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GenAIApi::class.java)
    }
}