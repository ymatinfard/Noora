package com.matin.noora.core.data.local

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.matin.noora.core.data.local.NooraDataStorePref.Keys.DATA_STORE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class NooraDataStorePref @Inject constructor(@ApplicationContext appContext: Context, coroutineScope: CoroutineScope) {

    private val Context.dataStore by preferencesDataStore(name = DATA_STORE_NAME)
    private val dataStore = appContext.dataStore

    @Volatile
    private var cachedToken: String? = "test_token"

    init {
        coroutineScope.launch {
            cachedToken = getToken()
        }
    }

    suspend fun putString(key: Preferences.Key<String>, value: String) {
        dataStore.edit { pref ->
            pref[key] = value
        }
    }

    suspend fun getString(key: Preferences.Key<String>): String {
        return dataStore.data.map { pref ->
            pref[key] ?: ""
        }.catch {
            emit("")
        }.first()
    }

    suspend fun getToken(): String {
        return getString(Keys.TOKEN)
    }

    suspend fun getRefreshToken(): String {
        return getString(Keys.REFRESH_TOKEN)
    }

    suspend fun saveToken(value: String) {
        putString(Keys.TOKEN, value)
        cachedToken = value
    }

    suspend fun saveRefreshToken(value: String) {
        putString(Keys.REFRESH_TOKEN, value)
    }

    fun getTokenImmediately() = cachedToken

    object Keys {
        const val DATA_STORE_NAME = "noora_pref"
        val TOKEN = stringPreferencesKey("token_key")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token_key")
    }
}