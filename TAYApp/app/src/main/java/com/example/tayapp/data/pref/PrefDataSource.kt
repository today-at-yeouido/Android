package com.example.tayapp.data.pref

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.tayapp.data.pref.model.UserPref
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")


class PrefDataSource @Inject constructor(@ApplicationContext val context: Context) {

    companion object {
        val LOGIN_USER_ACCESS_TOKEN = stringPreferencesKey("login_user_access_token")
        val LOGIN_USER_REFRESH_TOKEN = stringPreferencesKey("login_user_refresh_token")
        val LOGIN_USER_ID = stringPreferencesKey("login_user_id")
        val LOGIN_USER_EMAIL = stringPreferencesKey("login_user_email")

        val RECENT_SEARCH_TERM = stringPreferencesKey("recent_search_term")
        var TOKEN: String = ""

    }

    suspend fun saveRecentSearchTerm(query: String) {
        context.dataStore.edit {
            it[RECENT_SEARCH_TERM] = query
        }
    }

    fun getRecentSearchTerm(): Flow<String> = context.dataStore.data.map {
        it[RECENT_SEARCH_TERM] ?: ""
    }

    suspend fun setUser(user: UserPref) {
        context.dataStore.edit { settings ->
            settings[LOGIN_USER_ACCESS_TOKEN] = user.accessToken
            settings[LOGIN_USER_REFRESH_TOKEN] = user.refreshToken
            settings[LOGIN_USER_ID] = user.id
            settings[LOGIN_USER_EMAIL] = user.email
        }
    }

    fun getRefreshToken(): Flow<String> {
        return context.dataStore.data.map {
            it[LOGIN_USER_REFRESH_TOKEN] ?: ""
        }
    }

    fun getUser(): Flow<UserPref> {
        return context.dataStore.data.map { preference ->
            val accessToken = preference[LOGIN_USER_ACCESS_TOKEN] ?: ""
            val refreshToken = preference[LOGIN_USER_REFRESH_TOKEN] ?: ""
            val id = preference[LOGIN_USER_ID] ?: ""
            val email = preference[LOGIN_USER_EMAIL] ?: ""
            TOKEN = accessToken

            UserPref(accessToken, refreshToken, id, email)
        }
    }
}