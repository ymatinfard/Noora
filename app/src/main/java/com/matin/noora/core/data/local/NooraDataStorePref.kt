package com.matin.noora.core.data.local

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NooraDataStorePref @Inject constructor(@ApplicationContext appContext: Context) {

    val Context.dataStore by preferencesDataStore(name = DATA_STORE_NAME)
    val dataStore = appContext.dataStore

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

    companion object {
        const val DATA_STORE_NAME = "noora_pref"

        object Keys {
            val TOKEN = stringPreferencesKey("token_key")
            val REFRESH_TOKEN = stringPreferencesKey("refresh_token_key")
        }
    }
}