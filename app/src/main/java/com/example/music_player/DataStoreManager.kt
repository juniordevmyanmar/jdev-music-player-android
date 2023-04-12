package com.example.music_player

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager (private val context : Context) {

    private val USER_TOKEN_KEY = stringPreferencesKey("token")
    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore("my_data_store")
    }

    // read from data store
    val getAccessToken: Flow<String> = context.dataStore.data.map { mydatastore ->
        mydatastore[USER_TOKEN_KEY] ?: ""
    }

    // write to data store
    suspend fun saveToken(token: String) {
        context.dataStore.edit { mydatastore ->
            mydatastore[USER_TOKEN_KEY] = token
        }
    }


}