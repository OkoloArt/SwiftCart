package com.example.swiftcart.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "on_boarding_pref")

class DataStoreRepo(context: Context) {

    private object PreferencesKey {
        val onBoardingKey = booleanPreferencesKey(name = "on_boarding_completed")
        val jsonWebToken = stringPreferencesKey(name = "json_web_token")
        val isTokenExpired = booleanPreferencesKey(name = "is_token_expired")
        val hasLoggedIn = booleanPreferencesKey(name = "has_logged_in")
    }

    private val dataStore = context.dataStore

    suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.onBoardingKey] = completed
        }
    }

    suspend fun saveJsonWebToken(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.jsonWebToken] = token

        }
    }

    suspend fun setTokenExpired(isExpired: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.isTokenExpired] = isExpired
        }
    }

    suspend fun setHasLoggedIn(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.hasLoggedIn] = completed
        }
    }


    val tokenExpired: Flow<Boolean> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        preferences[PreferencesKey.isTokenExpired] ?: false
    }

    fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onBoardingState = preferences[PreferencesKey.onBoardingKey] ?: false
                onBoardingState
            }
    }

    fun readJsonWebToken(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val jsonWebToken = preferences[PreferencesKey.jsonWebToken] ?: ""
                jsonWebToken
            }
    }

    fun readHasLoggedInState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val hasLoggedIn = preferences[PreferencesKey.hasLoggedIn] ?: false
                hasLoggedIn
            }
    }
}