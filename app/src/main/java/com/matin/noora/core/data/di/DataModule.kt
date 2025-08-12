package com.matin.noora.core.data.di

import android.content.Context
import androidx.room.Room
import com.matin.noora.core.data.local.ChatDatabase
import com.matin.noora.core.data.local.ChatLocalRepositoryImpl
import com.matin.noora.core.data.local.SettingsRepositoryImpl
import com.matin.noora.core.data.AIRepositoryImpl
import com.matin.noora.core.data.local.MessageDao
import com.matin.noora.core.data.remote.NooraApi
import com.matin.noora.core.domain.repository.AIRepository
import com.matin.noora.core.domain.repository.ChatLocalRepository
import com.matin.noora.core.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier

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
    fun provideNooraApi(): NooraApi {
        return Retrofit.Builder()
            .baseUrl("https://noora.com/ai/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NooraApi::class.java)
    }

    @IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher {
        return kotlinx.coroutines.Dispatchers.IO
    }

    @Provides
    fun provideAppCoroutineScope(@IoDispatcher dispatcher: CoroutineDispatcher): CoroutineScope {
        return CoroutineScope(SupervisorJob() + dispatcher)
    }

    @MainDispatcher
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher {
        return kotlinx.coroutines.Dispatchers.Main
    }

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext applicationContext: Context): ChatDatabase =
        Room.databaseBuilder(
            applicationContext,
            ChatDatabase::class.java,
            "chat_database"
        ).build()

    @Provides
    @Singleton
    fun provideDao(db: ChatDatabase): MessageDao {
        return db.messageDao()
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher
