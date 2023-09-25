package com.example.github_user_application.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class SettingPreference private constructor(private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences>) {

    private val THEME_KEY = booleanPreferencesKey("theme_setting")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreference? = null

        fun getInstance(dataStore: DataStore<androidx.datastore.preferences.core.Preferences>): SettingPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}